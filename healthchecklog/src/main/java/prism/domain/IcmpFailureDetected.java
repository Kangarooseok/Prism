package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class IcmpFailureDetected extends AbstractEvent {

    private Long id;

    public IcmpFailureDetected(HealthCheckLog aggregate) {
        super(aggregate);
    }

    public IcmpFailureDetected() {
        super();
    }
}
//>>> DDD / Domain Event
