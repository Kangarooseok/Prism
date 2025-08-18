package prism.infra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prism.domain.cctv.repository.CctvErrorStatusRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CctvDiagnosisReadService {

    private final CctvErrorStatusRepository repo;

    /** 특정 CCTV의 최신 오류 상태 1건 반환 */
    public Optional<CctvErrorStatusRepository.Row> getLatest(long cctvId) {
        return repo.findLatestByCctvId(cctvId);
    }
}