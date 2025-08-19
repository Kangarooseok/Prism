package prism.infra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import prism.infra.service.CctvStatsService;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/cctvs")
@RequiredArgsConstructor
public class CctvStatsController {

    private final CctvStatsService statsService;

    // ===== 기존: 총 보유 =====

    // 현재 총보유량
    @GetMapping("/count")
    public Map<String, Long> getCurrentCount() {
        return Map.of("total", statsService.getCurrentTotal());
    }

    // 특정 날짜(YYYY-MM-DD)의 '그 날 종료 시점' 총보유량
    @GetMapping("/total-daily")
    public Map<String, Long> getTotalDaily(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return Map.of("total", statsService.getTotalAsOf(date));
    }

    // ===== 추가: 상태별 집계 =====

    // 오늘(현재 시점) 상태별 집계 { total, active, error, warning }
    @GetMapping("/status-counts")
    public Map<String, Long> getStatusCountsNow() {
        return statsService.getStatusCountsNow();
    }

    // 특정 날짜(YYYY-MM-DD) '그날 종료' 기준 상태별 집계 { total, active, error, warning }
    @GetMapping("/status-counts-daily")
    public Map<String, Long> getStatusCountsAsOf(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return statsService.getStatusCountsAsOf(date);
    }
}
