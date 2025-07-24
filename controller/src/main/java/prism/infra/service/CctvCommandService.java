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

@Service
@RequiredArgsConstructor
public class CctvCommandService {

    private final CctvRepository cctvRepository;
    private final EventPublisher eventPublisher;

    public Cctv register(RegisterCctvCommand command) {
        Cctv cctv = new Cctv();
        cctv.registerCctv(command);
        Cctv saved = cctvRepository.save(cctv);

        eventPublisher.publish(new CctvRegistered(saved));
        return saved;
    }

    public Cctv modify(Long id, ModifyCctvCommand command) throws Exception {
        Optional<Cctv> optional = cctvRepository.findById(id);
        if (optional.isEmpty()) throw new Exception("CCTV not found");

        Cctv cctv = optional.get();
        cctv.modifyCctv(command);
        Cctv saved = cctvRepository.save(cctv);

        eventPublisher.publish(new CctvModified(saved));
        return saved;
    }

    public void delete(Long id) throws Exception {
        Optional<Cctv> optional = cctvRepository.findById(id);
        if (optional.isEmpty()) throw new Exception("CCTV not found");

        Cctv cctv = optional.get();
        cctv.deleteCctv();
        cctvRepository.save(cctv);

        eventPublisher.publish(new CctvDeleted(cctv));
    }

    public Cctv get(Long id) throws Exception {
        return cctvRepository.findById(id)
                .orElseThrow(() -> new Exception("CCTV not found"));
    }

    public List<Cctv> getAll() {
        return (List<Cctv>) cctvRepository.findAll();
    }
}
