package prism.infra.controller;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prism.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/cctvs")
@Transactional
public class CctvController {

    @Autowired
    CctvRepository cctvRepository;

    @RequestMapping(
            value = "/cctvs/registercctv",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public Cctv registerCctv(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody RegisterCctvCommand registerCctvCommand
    ) throws Exception {
        System.out.println("##### /cctv/registerCctv  called #####");
        Cctv cctv = new Cctv();
        cctv.registerCctv(registerCctvCommand);
        cctvRepository.save(cctv);
        return cctv;
    }

    @RequestMapping(
            value = "/cctvs/{id}/modifycctv",
            method = RequestMethod.PUT,
            produces = "application/json;charset=UTF-8"
    )
    public Cctv modifyCctv(
            @PathVariable(value = "id") Long id,
            @RequestBody ModifyCctvCommand modifyCctvCommand,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /cctv/modifyCctv  called #####");
        Optional<Cctv> optionalCctv = cctvRepository.findById(id);

        optionalCctv.orElseThrow(() -> new Exception("No Entity Found"));
        Cctv cctv = optionalCctv.get();
        cctv.modifyCctv(modifyCctvCommand);

        cctvRepository.save(cctv);
        return cctv;
    }

    @RequestMapping(
            value = "/cctvs/{id}/deletecctv",
            method = RequestMethod.DELETE,
            produces = "application/json;charset=UTF-8"
    )
    public Cctv deleteCctv(
            @PathVariable(value = "id") Long id,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /cctv/deleteCctv  called #####");
        Optional<Cctv> optionalCctv = cctvRepository.findById(id);

        optionalCctv.orElseThrow(() -> new Exception("No Entity Found"));
        Cctv cctv = optionalCctv.get();
        cctv.deleteCctv();

        cctvRepository.delete(cctv);
        return cctv;
    }
}
//>>> Clean Arch / Inbound Adaptor
