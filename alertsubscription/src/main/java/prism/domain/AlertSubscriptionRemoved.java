package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class AlertSubscriptionRemoved extends AbstractEvent {

    private Long id;
    private String userId;
    private Date removedAt;

    public AlertSubscriptionRemoved(AlertSubscription aggregate) {
        super(aggregate);
    }

    public AlertSubscriptionRemoved() {
        super();
    }
}
//>>> DDD / Domain Event
