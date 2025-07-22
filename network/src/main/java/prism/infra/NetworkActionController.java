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
// @RequestMapping(value="/networkActions")
@Transactional
public class NetworkActionController {

    @Autowired
    NetworkActionRepository networkActionRepository;

    @RequestMapping(
        value = "/networkActions/dispatchtosite",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public NetworkAction dispatchToSite(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody DispatchToSiteCommand dispatchToSiteCommand
    ) throws Exception {
        System.out.println("##### /networkAction/dispatchToSite  called #####");
        NetworkAction networkAction = new NetworkAction();
        networkAction.dispatchToSite(dispatchToSiteCommand);
        networkActionRepository.save(networkAction);
        return networkAction;
    }

    @RequestMapping(
        value = "/networkActions/raisenetworkalarm",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public NetworkAction raiseNetworkAlarm(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody RaiseNetworkAlarmCommand raiseNetworkAlarmCommand
    ) throws Exception {
        System.out.println(
            "##### /networkAction/raiseNetworkAlarm  called #####"
        );
        NetworkAction networkAction = new NetworkAction();
        networkAction.raiseNetworkAlarm(raiseNetworkAlarmCommand);
        networkActionRepository.save(networkAction);
        return networkAction;
    }

    @RequestMapping(
        value = "/networkActions/resolvenetworkissue",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public NetworkAction resolveNetworkIssue(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody ResolveNetworkIssueCommand resolveNetworkIssueCommand
    ) throws Exception {
        System.out.println(
            "##### /networkAction/resolveNetworkIssue  called #####"
        );
        NetworkAction networkAction = new NetworkAction();
        networkAction.resolveNetworkIssue(resolveNetworkIssueCommand);
        networkActionRepository.save(networkAction);
        return networkAction;
    }
}
//>>> Clean Arch / Inbound Adaptor
