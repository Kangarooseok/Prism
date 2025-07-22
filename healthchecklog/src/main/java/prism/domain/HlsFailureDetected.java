package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class HlsFailureDetected extends AbstractEvent {

    private Long id;

    public HlsFailureDetected(HealthCheckLog aggregate) {
        super(aggregate);
    }

    public HlsFailureDetected() {
        super();
    }
}
//>>> DDD / Domain Event
