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
// @RequestMapping(value="/alertSubscriptions")
@Transactional
public class AlertSubscriptionController {

    @Autowired
    AlertSubscriptionRepository alertSubscriptionRepository;

    @RequestMapping(
        value = "/alertSubscriptions/subscribetoalert",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public AlertSubscription subscribeToAlert(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody SubscribeToAlertCommand subscribeToAlertCommand
    ) throws Exception {
        System.out.println(
            "##### /alertSubscription/subscribeToAlert  called #####"
        );
        AlertSubscription alertSubscription = new AlertSubscription();
        alertSubscription.subscribeToAlert(subscribeToAlertCommand);
        alertSubscriptionRepository.save(alertSubscription);
        return alertSubscription;
    }

    @RequestMapping(
        value = "/alertSubscriptions/{id}/unsubscribefromalert",
        method = RequestMethod.DELETE,
        produces = "application/json;charset=UTF-8"
    )
    public AlertSubscription unsubscribeFromAlert(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println(
            "##### /alertSubscription/unsubscribeFromAlert  called #####"
        );
        Optional<AlertSubscription> optionalAlertSubscription = alertSubscriptionRepository.findById(
            id
        );

        optionalAlertSubscription.orElseThrow(() ->
            new Exception("No Entity Found")
        );
        AlertSubscription alertSubscription = optionalAlertSubscription.get();
        alertSubscription.unsubscribeFromAlert();

        alertSubscriptionRepository.delete(alertSubscription);
        return alertSubscription;
    }
}
//>>> Clean Arch / Inbound Adaptor
