package prism.infra.job;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import prism.domain.cctv.model.Cctv;
import prism.domain.cctv.repository.CctvRepository;
import prism.infra.service.CctvLiveStatusService;

@Component
@RequiredArgsConstructor
public class CctvStatusSyncJob {

    private final CctvRepository cctvRepository;
    private final CctvLiveStatusService liveStatusService;

    @Scheduled(fixedDelay = 60_000)
    public void sync() {
        int page = 0, size = 200; // 배치 크기
        Page<Cctv> slice;
        do {
            slice = cctvRepository.findAll(PageRequest.of(page++, size));
            slice.forEach(c -> {
                String live = liveStatusService.resolveStatusOrActive(c.getId());
                if (live != null && !live.equals(c.getStatus())) {
                    int tries = 0;
                    while (true) {
                        try {
                            // 개별 커밋
                            c.setStatus(live);
                            cctvRepository.saveAndFlush(c); // JpaRepository에서 제공
                            break;
                        } catch (OptimisticLockingFailureException e) {
                            if (++tries > 1) break; // 2회차까지만 재시도
                        }
                    }
                }
            });
        } while (!slice.isLast());
    }
}
