package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class ResourceFailureDetected extends AbstractEvent {

    private Long id;

    public ResourceFailureDetected(HealthCheckLog aggregate) {
        super(aggregate);
    }

    public ResourceFailureDetected() {
        super();
    }
}
//>>> DDD / Domain Event
