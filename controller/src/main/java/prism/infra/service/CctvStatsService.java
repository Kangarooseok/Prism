package prism.infra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prism.domain.cctv.repository.CctvRepository;

import java.sql.Timestamp;
import java.time.*;

@Service
@RequiredArgsConstructor
public class CctvStatsService {

    private final CctvRepository cctvRepository;
    private static final ZoneId ZONE_KST = ZoneId.of("Asia/Seoul");

    // 현재 총보유(삭제되지 않은 CCTV 수)
    public long getCurrentTotal() {
        return cctvRepository.countActiveNative();
    }

    // 특정 날짜(YYYY-MM-DD)의 '하루 끝(KST 23:59:59.999)' 시점 기준 총보유
    public long getTotalAsOf(LocalDate date) {
        // KST 기준으로 해당 날짜의 끝을 cutoff으로 사용
        ZonedDateTime endOfDayKst = date.plusDays(1).atStartOfDay(ZONE_KST).minusNanos(1);
        Instant cutoff = endOfDayKst.toInstant(); // UTC Instant
        return cctvRepository.countAsOf(Timestamp.from(cutoff));
    }
}
