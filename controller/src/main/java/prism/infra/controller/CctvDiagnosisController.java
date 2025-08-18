package prism.infra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prism.infra.service.CctvDiagnosisReadService;

import java.util.Map;

@RestController
@RequestMapping("/cctvs")
@RequiredArgsConstructor
public class CctvDiagnosisController {

    private final CctvDiagnosisReadService service;

    // 특정 CCTV의 최신 오류 상태
    @GetMapping("/{id}/error-status")
    public Map<String, Object> getLatest(@PathVariable("id") Long id) {
        return service.getLatest(id)
                .<Map<String, Object>>map(r -> Map.of(
                        "cctvId", r.cctvId,
                        "status", r.status,
                        "occurredAt", r.occurredAt
                ))
                .orElse(Map.of("cctvId", id, "status", "UNKNOWN"));
    }
}
