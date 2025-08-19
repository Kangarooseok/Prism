package prism.infra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prism.domain.cctv.command.ModifyCctvCommand;
import prism.domain.cctv.command.RegisterCctvCommand;
//import prism.domain.cctv.event.CctvDeleted;
//import prism.domain.cctv.event.CctvModified;
//import prism.domain.cctv.event.CctvRegistered;
import prism.domain.cctv.model.Cctv;
import prism.domain.cctv.repository.CctvRepository;
import prism.domain.group.model.CctvGroup;
import prism.domain.group.repository.CctvGroupRepository;
//import prism.infra.EventPublisher;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
public class CctvCommandService {

    private final CctvRepository cctvRepository;
    private final CctvGroupRepository cctvGroupRepository;
//    private final EventPublisher eventPublisher;

    // 장애판별(Read) DB에서 최신 상태를 가져오는 서비스
    private final CctvLiveStatusService liveStatusService;

    @PersistenceContext
    private EntityManager entityManager;

    // CCTV 등록
    public Cctv register(RegisterCctvCommand command) {
        CctvGroup group;

        // 그룹이 지정되지 않은 경우 '미정' 그룹에 자동 등록
        if (command.getGroupId() != null) {
            group = cctvGroupRepository.findById(command.getGroupId())
                    .orElseThrow(() -> new RuntimeException("그룹이 존재하지 않습니다"));
        } else {
            group = cctvGroupRepository.findByName("미정")
                    .orElseThrow(() -> new RuntimeException("'미정' 그룹이 존재하지 않습니다"));
        }

        Cctv cctv = new Cctv();
        cctv.registerCctv(command);
        cctv.setGroup(group);

        Cctv saved = cctvRepository.save(cctv);

        // 등록 이벤트 발행 (Kafka)
//        eventPublisher.publish(new CctvRegistered(saved));
        return saved;
    }

    // CCTV 수정
    public Cctv modify(Long id, ModifyCctvCommand command) throws Exception {
        Optional<Cctv> optional = cctvRepository.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("CCTV not found");
        }

        Cctv cctv = optional.get();
        cctv.modifyCctv(command);

        // 그룹 변경 요청이 있을 경우 JPA 관리 객체로 주입
        if (command.getGroupId() != null) {
            CctvGroup group = cctvGroupRepository.findById(command.getGroupId())
                    .orElseThrow(() -> new RuntimeException("그룹이 존재하지 않습니다"));
            cctv.setGroup(group);
        }

        Cctv saved = cctvRepository.save(cctv);

        // 수정 이벤트 발행 (Kafka)
//        eventPublisher.publish(new CctvModified(saved));
        return saved;
    }

    // CCTV 삭제
    public void delete(Long id) throws Exception {
        Optional<Cctv> optional = cctvRepository.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("CCTV not found");
        }

        Cctv cctv = optional.get();

        // 삭제 이벤트 발행 (Kafka)
//        eventPublisher.publish(new CctvDeleted(cctv));

        // DB 삭제
        cctvRepository.delete(cctv);
    }

    // CCTV 단건 조회 (장애판별 DB 상태 주입, 없으면 ACTIVE)
    @Transactional(readOnly = true)
    public Cctv get(Long id) throws Exception {
        Cctv cctv = cctvRepository.findById(id)
                .orElseThrow(() -> new Exception("CCTV not found"));

        String live = liveStatusService.resolveStatusOrActive(cctv.getId());

        // 읽기 트랜잭션에서 영속성 컨텍스트의 더티체킹으로 UPDATE가 나가지 않게 분리
        entityManager.detach(cctv);
        cctv.setStatus(live); // 응답 용도로만 덮어쓰기

        return cctv;
    }

    // CCTV 전체 조회 (각 항목에 라이브 상태 주입, 없으면 ACTIVE)
    @Transactional(readOnly = true)
    public List<Cctv> getAll() {
        Iterable<Cctv> iterable = cctvRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(c -> {
                    String live = liveStatusService.resolveStatusOrActive(c.getId());
                    entityManager.detach(c);
                    c.setStatus(live); // 응답 용도로만 덮어쓰기
                    return c;
                })
                .collect(Collectors.toList());
    }

    public Long countCreatedBetween(Date start, Date end) {
        return cctvRepository.countByCreatedAtBetween(start, end);
    }
}
