package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class HealthCheckScheduled extends AbstractEvent {

    private Long id;
    private String scheduledAt;

    public HealthCheckScheduled(HealthCheckLog aggregate) {
        super(aggregate);
    }

    public HealthCheckScheduled() {
        super();
    }
}
//>>> DDD / Domain Event
