package prism.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import prism.AlertsubscriptionApplication;
import prism.domain.AlertSubscriptionCreated;
import prism.domain.AlertSubscriptionRemoved;

@Entity
@Table(name = "AlertSubscription_table")
@Data
//<<< DDD / Aggregate Root
public class AlertSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;

    private alertType alertType;

    private severityLevel severityLevel;

    private Date createdAt;

    private Boolean isActive;

    @PostPersist
    public void onPostPersist() {
        AlertSubscriptionCreated alertSubscriptionCreated = new AlertSubscriptionCreated(
            this
        );
        alertSubscriptionCreated.publishAfterCommit();

        AlertSubscriptionRemoved alertSubscriptionRemoved = new AlertSubscriptionRemoved(
            this
        );
        alertSubscriptionRemoved.publishAfterCommit();
    }

    public static AlertSubscriptionRepository repository() {
        AlertSubscriptionRepository alertSubscriptionRepository = AlertsubscriptionApplication.applicationContext.getBean(
            AlertSubscriptionRepository.class
        );
        return alertSubscriptionRepository;
    }

    //<<< Clean Arch / Port Method
    public void subscribeToAlert(
        SubscribeToAlertCommand subscribeToAlertCommand
    ) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void unsubscribeFromAlert() {
        //implement business logic here:

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
