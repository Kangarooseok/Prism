package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class AlertSubscriptionCreated extends AbstractEvent {

    private Long id;
    private String userId;
    private alertType alertType;
    private severityLevel severityLevel;
    private Date createdAt;

    public AlertSubscriptionCreated(AlertSubscription aggregate) {
        super(aggregate);
    }

    public AlertSubscriptionCreated() {
        super();
    }
}
//>>> DDD / Domain Event
