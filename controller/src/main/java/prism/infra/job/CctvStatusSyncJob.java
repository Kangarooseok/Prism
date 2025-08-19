package prism.infra.job;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import prism.domain.cctv.model.Cctv;
import prism.infra.service.CctvLiveStatusService;
import prism.domain.cctv.repository.CctvRepository;

@Component
@RequiredArgsConstructor
public class CctvStatusSyncJob {
    private final CctvRepository cctvRepository;
    private final CctvLiveStatusService liveStatusService;

    @Scheduled(fixedDelay = 60_000) // 60초마다
    @Transactional
    public void sync() {
        for (Cctv c : cctvRepository.findAll()) {
            String live = liveStatusService.resolveStatusOrActive(c.getId());
            if (!live.equals(c.getStatus())) {
                c.setStatus(live); // 변경 시에만 UPDATE
            }
        }
    }
}
