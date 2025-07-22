package prism.infra;

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
// @RequestMapping(value="/tvResolutions")
@Transactional
public class TvResolutionController {

    @Autowired
    TvResolutionRepository tvResolutionRepository;

    @RequestMapping(
        value = "/tvResolutions/dispatch",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public TvResolution dispatch(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody DispatchCommand dispatchCommand
    ) throws Exception {
        System.out.println("##### /tvResolution/dispatch  called #####");
        TvResolution tvResolution = new TvResolution();
        tvResolution.dispatch(dispatchCommand);
        tvResolutionRepository.save(tvResolution);
        return tvResolution;
    }

    @RequestMapping(
        value = "/tvResolutions/triggerwarning",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public TvResolution triggerWarning(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody TriggerWarningCommand triggerWarningCommand
    ) throws Exception {
        System.out.println("##### /tvResolution/triggerWarning  called #####");
        TvResolution tvResolution = new TvResolution();
        tvResolution.triggerWarning(triggerWarningCommand);
        tvResolutionRepository.save(tvResolution);
        return tvResolution;
    }

    @RequestMapping(
        value = "/tvResolutions/resolvesituation",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public TvResolution resolveSituation(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody ResolveSituationCommand resolveSituationCommand
    ) throws Exception {
        System.out.println(
            "##### /tvResolution/resolveSituation  called #####"
        );
        TvResolution tvResolution = new TvResolution();
        tvResolution.resolveSituation(resolveSituationCommand);
        tvResolutionRepository.save(tvResolution);
        return tvResolution;
    }
}
//>>> Clean Arch / Inbound Adaptor
