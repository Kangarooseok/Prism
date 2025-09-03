package prism.infra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prism.infra.dto.AssignRequest;
import prism.infra.service.SubscriptionService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionsController {

    private final SubscriptionService service;

    // 특정 그룹에 배정된 담당자 userId 목록
    @GetMapping("/by-group/{groupId}")
    public List<Long> getManagers(@PathVariable long groupId) {
        return service.getManagerIdsByGroup(groupId);
    }

    // 담당자 배정 (단일 그룹 원칙)
    @PostMapping
    public Map<String, String> assign(@RequestBody AssignRequest req) {
        service.assign(req.groupId(), req.userId());
        return Map.of("result", "ok");
    }

    // 담당자 해제 → 미정(2)으로 이동
    @DeleteMapping
    public Map<String, String> unassign(@RequestParam long groupId, @RequestParam long userId) {
        service.unassignToUnassigned(groupId, userId);
        return Map.of("result", "ok");
    }
}
