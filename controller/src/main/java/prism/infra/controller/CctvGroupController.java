package prism.infra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prism.infra.dto.CctvGroupRequest;
import prism.infra.dto.CctvGroupResponse;
import prism.infra.service.CctvGroupCommandService;

import java.util.List;

@RestController
@RequestMapping("/cctv-groups")
@RequiredArgsConstructor
public class CctvGroupController {

    private final CctvGroupCommandService service;

    // 그룹 등록
    @PostMapping
    public CctvGroupResponse create(@RequestBody CctvGroupRequest request) {
        return service.register(request);
    }

    // 그룹 수정
    @PutMapping("/{id}")
    public CctvGroupResponse update(@PathVariable Long id, @RequestBody CctvGroupRequest request) {
        return service.update(id, request);
    }

    // 그룹 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // 그룹 단건 조회
    @GetMapping("/{id}")
    public CctvGroupResponse get(@PathVariable Long id) throws Exception {
        return service.get(id);
    }

    // 그룹 전체 조회
    @GetMapping
    public List<CctvGroupResponse> getAll() {
        return service.getAll();
    }
}
