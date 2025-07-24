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
import prism.infra.EventPublisher;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CctvCommandService {

    private final CctvRepository cctvRepository;
    private final EventPublisher eventPublisher;

    /**
     * CCTV 등록
     */
    public Cctv register(RegisterCctvCommand command) {
        Cctv cctv = new Cctv();
        cctv.registerCctv(command);
        Cctv saved = cctvRepository.save(cctv);

        // Kafka 메시지 발행
        eventPublisher.publish(new CctvRegistered(saved));
        return saved;
    }

    /**
     * CCTV 수정
     */
    public Cctv modify(Long id, ModifyCctvCommand command) throws Exception {
        Optional<Cctv> optional = cctvRepository.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("CCTV not found");
        }

        Cctv cctv = optional.get();
        cctv.modifyCctv(command);
        Cctv saved = cctvRepository.save(cctv);

        // Kafka 메시지 발행
        eventPublisher.publish(new CctvModified(saved));
        return saved;
    }

    /**
     * CCTV 삭제
     */
    public void delete(Long id) throws Exception {
        Optional<Cctv> optional = cctvRepository.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("CCTV not found");
        }

        Cctv cctv = optional.get();

        // Kafka 메시지 발행 - 삭제되기 전 정보 기준
        eventPublisher.publish(new CctvDeleted(cctv));

        // DB에서 삭제
        cctvRepository.delete(cctv);
    }

    /**
     * CCTV 단건 조회
     */
    public Cctv get(Long id) throws Exception {
        return cctvRepository.findById(id)
                .orElseThrow(() -> new Exception("CCTV not found"));
    }

    /**
     * CCTV 전체 조회
     */
    public List<Cctv> getAll() {
        Iterable<Cctv> iterable = cctvRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

}
