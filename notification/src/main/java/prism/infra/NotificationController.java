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
// @RequestMapping(value="/notifications")
@Transactional
public class NotificationController {

    @Autowired
    NotificationRepository notificationRepository;

    @RequestMapping(
        value = "/notifications/createnotification",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Notification createNotification(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody CreateNotificationCommand createNotificationCommand
    ) throws Exception {
        System.out.println(
            "##### /notification/createNotification  called #####"
        );
        Notification notification = new Notification();
        notification.createNotification(createNotificationCommand);
        notificationRepository.save(notification);
        return notification;
    }
}
//>>> Clean Arch / Inbound Adaptor
