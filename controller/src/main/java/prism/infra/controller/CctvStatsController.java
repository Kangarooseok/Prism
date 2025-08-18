package prism.infra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import prism.infra.service.CctvStatsService;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/cctvs")
@RequiredArgsConstructor
public class CctvStatsController {

    private final CctvStatsService statsService;

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
}
