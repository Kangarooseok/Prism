package prism.infra.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prism.domain.cctv.command.ModifyCctvCommand;
import prism.domain.cctv.command.RegisterCctvCommand;
import prism.domain.cctv.model.Cctv;
import prism.infra.service.CctvCommandService;

import java.util.List;

@RestController
@RequestMapping("/cctvs") // <-- 변경됨
@RequiredArgsConstructor
public class CctvController {

    private final CctvCommandService cctvCommandService;

    @PostMapping
    public Cctv register(@RequestBody RegisterCctvCommand command) {
        return cctvCommandService.register(command);
    }

    @PostConstruct
    public void init() {
        System.out.println("✅ CctvController 로딩됨");
    }

    @PutMapping("/{id}")
    public Cctv modify(@PathVariable Long id, @RequestBody ModifyCctvCommand command) throws Exception {
        return cctvCommandService.modify(id, command);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        cctvCommandService.delete(id);
    }

    @GetMapping("/{id}")
    public Cctv get(@PathVariable Long id) throws Exception {
        return cctvCommandService.get(id);
    }

    @GetMapping
    public List<Cctv> getAll() {
        return cctvCommandService.getAll();
    }
}
