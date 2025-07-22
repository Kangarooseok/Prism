package prism.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import prism.NotificationApplication;
import prism.domain.NotificationSent;

@Entity
@Table(name = "Notification_table")
@Data
//<<< DDD / Aggregate Root
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String receiver;

    private String message;

    private String status;

    private Date createdAt;

    private Date sentAt;

    private Date updatedAt;

    private Boolean issued;

    private String healthCheckId;

    @PostPersist
    public void onPostPersist() {
        NotificationSent notificationSent = new NotificationSent(this);
        notificationSent.publishAfterCommit();
    }

    public static NotificationRepository repository() {
        NotificationRepository notificationRepository = NotificationApplication.applicationContext.getBean(
            NotificationRepository.class
        );
        return notificationRepository;
    }

    //<<< Clean Arch / Port Method
    public void createNotification(
        CreateNotificationCommand createNotificationCommand
    ) {
        //implement business logic here:

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
