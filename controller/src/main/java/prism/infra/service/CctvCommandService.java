package prism.infra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prism.domain.cctv.command.ModifyCctvCommand;
import prism.domain.cctv.command.RegisterCctvCommand;
import prism.domain.cctv.event.CctvDeleted;
import prism.domain.cctv.event.CctvModified;
import prism.domain.cctv.event.CctvRegistered;
import prism.domain.cctv.model.Cctv;
import prism.domain.cctv.repository.CctvRepository;
import prism.domain.group.model.CctvGroup;
import prism.domain.group.repository.CctvGroupRepository;
import prism.infra.EventPublisher;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CctvCommandService {

    private final CctvRepository cctvRepository;
    private final CctvGroupRepository cctvGroupRepository;
    private final EventPublisher eventPublisher;

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
        eventPublisher.publish(new CctvRegistered(saved));
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
        Cctv saved = cctvRepository.save(cctv);

        // 수정 이벤트 발행 (Kafka)
        eventPublisher.publish(new CctvModified(saved));
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
        eventPublisher.publish(new CctvDeleted(cctv));

        // DB 삭제
        cctvRepository.delete(cctv);
    }

    // CCTV 단건 조회
    public Cctv get(Long id) throws Exception {
        return cctvRepository.findById(id)
                .orElseThrow(() -> new Exception("CCTV not found"));
    }

    // CCTV 전체 조회
    public List<Cctv> getAll() {
        Iterable<Cctv> iterable = cctvRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
