package prism.infra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prism.domain.cctv.model.Cctv;
import prism.domain.cctv.repository.CctvRepository;

import java.sql.Timestamp;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CctvStatsService {

    private final CctvRepository cctvRepository;

    // 장애판별(Read) DB 접근 DAO (NamedParameterJdbcTemplate 기반)
    private final CctvStatusReadDao statusReadDao;

    private static final ZoneId ZONE_KST = ZoneId.of("Asia/Seoul");

    // ========= 총 보유 수(기존) =========
    public long getCurrentTotal() {
        return cctvRepository.countActiveNative();
    }

    public long getTotalAsOf(LocalDate date) {
        Instant cutoff = kstEndOfDay(date);
        return cctvRepository.countAsOf(Timestamp.from(cutoff));
    }

    // ========= 새로 추가: 프론트가 바로 쓰는 상태별 집계 =========
    // 오늘(현재 시점) 상태별 카운트 { total, active, error, warning }
    @Transactional(readOnly = true)
    public Map<String, Long> getStatusCountsNow() {
        Instant now = Instant.now();

        // 운영 중인 CCTV ID 목록(삭제 제외)
        List<Long> activeCctvIds = getActiveCctvIds();

        // Read DB 스냅샷
        Map<Long, String> snap = statusReadDao.findLatestStatusAsOf(now);

        Counts cnt = countByStatus(activeCctvIds, snap);

        return Map.of(
                "total", (long) activeCctvIds.size(),
                "active", cnt.active,
                "error", cnt.error,
                "warning", cnt.warning
        );
    }

    // 특정 날짜(KST) ‘그날 종료’ 기준 상태별 카운트 { total, active, error, warning }
    @Transactional(readOnly = true)
    public Map<String, Long> getStatusCountsAsOf(LocalDate date) {
        Instant cutoff = kstEndOfDay(date);

        // ⬇️ 어제(혹은 요청일) '그날 종료' 기준 총보유 수
        long totalAsOf = cctvRepository.countAsOf(Timestamp.from(cutoff)); // ← 핵심 변경

        // 상태별 집계용 스냅샷은 여전히 cutoff 기준
        Map<Long, String> snap = statusReadDao.findLatestStatusAsOf(cutoff);

        // (빠른 수정) 상태별 분모로 '현재 살아있는 CCTV 목록'을 그대로 사용
        // 필요 시 아래 '정교한 수정안' 참고해 as-of ID 목록으로 교체 가능
        List<Long> activeCctvIdsAtNow = getActiveCctvIds();
        Counts cnt = countByStatus(activeCctvIdsAtNow, snap);

        return Map.of(
                "total",   totalAsOf,     // ← 현재 size() 대신 as-of 값 사용
                "active",  cnt.active,
                "error",   cnt.error,
                "warning", cnt.warning
        );
    }

    // ========= 대시보드 증감률 용(원하면 사용) =========
    public record Metric(long current, long previous, double deltaPct) {}
    public record StatusMetrics(Metric active, Metric warning, Metric error) {}

    @Transactional(readOnly = true)
    public StatusMetrics getStatusMetrics() {
        Instant now = Instant.now();

        // 전일 23:59:59.999(KST)
        LocalDate yesterday = LocalDate.now(ZONE_KST).minusDays(1);
        Instant prevCutoff = kstEndOfDay(yesterday);

        List<Long> activeCctvIds = getActiveCctvIds();

        Map<Long, String> curr = statusReadDao.findLatestStatusAsOf(now);
        Map<Long, String> prev = statusReadDao.findLatestStatusAsOf(prevCutoff);

        Counts currCnt = countByStatus(activeCctvIds, curr);
        Counts prevCnt = countByStatus(activeCctvIds, prev);

        return new StatusMetrics(
                metric(currCnt.active,  prevCnt.active),
                metric(currCnt.warning, prevCnt.warning),
                metric(currCnt.error,   prevCnt.error)
        );
    }

    // ========= 내부 유틸 =========
    private static class Counts {
        long active, warning, error;
        Counts(long a, long w, long e) { this.active = a; this.warning = w; this.error = e; }
    }

    private List<Long> getActiveCctvIds() {
        // 엔티티에 @Where(deleted_at is null)이 있다면 findAll() 자체가 삭제 제외가 되지만,
        // 안전하게 deletedAt == null로 한 번 더 거릅니다.
        return StreamSupport.stream(cctvRepository.findAll().spliterator(), false)
                .filter(c -> c.getDeletedAt() == null)
                .map(Cctv::getId)
                .collect(Collectors.toList());
    }

    // 스냅샷에 값이 없거나 미정의인 경우 ACTIVE로 간주(요구사항)
    private Counts countByStatus(List<Long> cctvIds, Map<Long, String> snapshot) {
        long active = 0, warning = 0, error = 0;
        for (Long id : cctvIds) {
            String raw = snapshot.get(id);
            String s = (raw == null ? "ACTIVE" : raw.trim().toUpperCase());

            switch (s) {
                // 오류 계열
                case "ERROR", "OFFLINE", "DOWN", "FAIL", "FAILED", "CRITICAL" -> error++;
                // 주의 계열
                case "WARNING", "WARN", "ALERT" -> warning++;
                // 정상 계열
                case "ACTIVE", "OK", "ONLINE", "NORMAL" -> active++;
                // 모르는 값은 안전하게 정상 처리 (원하면 warning으로 보내도 됨)
                default -> active++;
            }
        }
        return new Counts(active, warning, error);
    }


    private Metric metric(long cur, long prev) {
        return new Metric(cur, prev, pctChange(cur, prev));
    }

    private double pctChange(long cur, long prev) {
        if (prev == 0) return (cur > 0) ? 100.0 : 0.0;
        return ((double) (cur - prev) / (double) prev) * 100.0;
    }

    private Instant kstEndOfDay(LocalDate date) {
        return date.plusDays(1).atStartOfDay(ZONE_KST).minusNanos(1).toInstant();
    }
}
