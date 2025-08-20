package prism.infra.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prism.domain.cctv.command.ModifyCctvCommand;
import prism.domain.cctv.command.RegisterCctvCommand;
import prism.domain.cctv.model.Cctv;
import prism.infra.dto.CctvRequest;
import prism.infra.dto.CctvResponse;
import prism.infra.service.CctvCommandService;

import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cctvs")
@RequiredArgsConstructor
public class CctvController {

    private final CctvCommandService cctvCommandService;

    // CCTV 등록
    @PostMapping
    public Cctv register(@RequestBody CctvRequest request) {
        RegisterCctvCommand command = new RegisterCctvCommand();
        command.setLocationName(request.getLocationName());
        command.setLocationAddress(request.getLocationAddress());
        command.setIpAddress(request.getIpAddress());
        command.setHlsAddress(request.getHlsAddress());
        command.setLongitude(request.getLongitude());
        command.setLatitude(request.getLatitude());
        command.setGroupId(request.getGroupId());
        command.setStatus(request.getStatus());

        return cctvCommandService.register(command);
    }

    // CCTV 수정
    @PutMapping("/{id}")
    public CctvResponse modify(@PathVariable Long id, @RequestBody ModifyCctvCommand command) throws Exception {
        Cctv cctv = cctvCommandService.modify(id, command);

        CctvResponse response = new CctvResponse();
        response.setId(cctv.getId());
        response.setLocationName(cctv.getLocationName());
        response.setLocationAddress(cctv.getLocationAddress());
        response.setIpAddress(cctv.getIpAddress());
        response.setHlsAddress(cctv.getHlsAddress());
        response.setLongitude(cctv.getLongitude());
        response.setLatitude(cctv.getLatitude());
        response.setStatus(cctv.getStatus());

        if (cctv.getGroup() != null) {
            response.setGroupId(cctv.getGroup().getId());
            response.setGroupName(cctv.getGroup().getName());
        }

        return response;
    }

    // CCTV 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        cctvCommandService.delete(id);
    }

    // CCTV 단건 조회
    @GetMapping("/{id}")
    public Cctv get(@PathVariable Long id) throws Exception {
        return cctvCommandService.get(id);
    }

    // CCTV 전체 조회
    @GetMapping
    public List<CctvResponse> getAll() {
        List<Cctv> cctvs = cctvCommandService.getAll();

        return cctvs.stream().map(cctv -> {
            CctvResponse response = new CctvResponse();
            response.setId(cctv.getId());
            response.setLocationName(cctv.getLocationName());
            response.setLocationAddress(cctv.getLocationAddress());
            response.setIpAddress(cctv.getIpAddress());
            response.setHlsAddress(cctv.getHlsAddress());
            response.setLongitude(cctv.getLongitude());
            response.setLatitude(cctv.getLatitude());
            response.setStatus(cctv.getStatus());

            if (cctv.getGroup() != null) {
                response.setGroupId(cctv.getGroup().getId());
                response.setGroupName(cctv.getGroup().getName());
            }

            return response;
        }).toList();
    }

    // 컨트롤러 로딩 확인용 로그
    @PostConstruct
    public void init() {
        System.out.println("CctvController 로딩됨");
    }

    //
    @GetMapping("/daily-count")
    public Map<String, Long> getDailyCounts() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        Date todayStart = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date todayEnd = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        Date yesterdayStart = Date.from(yesterday.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date yesterdayEnd = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Long todayCount = cctvCommandService.countCreatedBetween(todayStart, todayEnd);
        Long yesterdayCount = cctvCommandService.countCreatedBetween(yesterdayStart, yesterdayEnd);

        Map<String, Long> result = new HashMap<>();
        result.put("today", todayCount);
        result.put("yesterday", yesterdayCount);
        return result;
    }
}
