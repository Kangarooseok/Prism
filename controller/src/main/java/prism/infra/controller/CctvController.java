package prism.infra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import prism.domain.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
<<<<<<< HEAD
import java.util.List;
import java.util.Optional;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prism.domain.*;
import prism.domain.cctv.model.Cctv;

//<<< Clean Arch / Inbound Adaptor
>>>>>>> controller

@RestController
@Transactional
@RequestMapping("/cctvs")
public class CctvController {

    @Autowired
    CctvRepository cctvRepository;

    // 1. 등록
    @PostMapping("/register")
    public Cctv registerCctv(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody RegisterCctvCommand command
    ) throws Exception {
        Cctv cctv = new Cctv();
        cctv.registerCctv(command);
        return cctvRepository.save(cctv);
    }

    // 2. 수정
    @PutMapping("/{id}/modify")
    public Cctv modifyCctv(
            @PathVariable Long id,
            @RequestBody ModifyCctvCommand command
    ) throws Exception {
        Optional<Cctv> optionalCctv = cctvRepository.findById(id);
        optionalCctv.orElseThrow(() -> new Exception("No Entity Found"));
        Cctv cctv = optionalCctv.get();
        cctv.modifyCctv(command);
        return cctvRepository.save(cctv);
    }

    // 3. 삭제
    @DeleteMapping("/{id}")
    public String deleteCctv(@PathVariable Long id) throws Exception {
        Optional<Cctv> optionalCctv = cctvRepository.findById(id);
        optionalCctv.orElseThrow(() -> new Exception("No Entity Found"));
        Cctv cctv = optionalCctv.get();
        cctv.deleteCctv();
        cctvRepository.delete(cctv);
        return "Deleted CCTV with ID: " + id;
    }

    // 4. 단건 조회
    @GetMapping("/{id}")
    public Cctv getCctv(@PathVariable Long id) throws Exception {
        return cctvRepository.findById(id).orElseThrow(() -> new Exception("No Entity Found"));
    }

    // 5. 전체 조회
    @GetMapping
    public List<Cctv> getAllCctvs() {
        return (List<Cctv>) cctvRepository.findAll();
    }
}
