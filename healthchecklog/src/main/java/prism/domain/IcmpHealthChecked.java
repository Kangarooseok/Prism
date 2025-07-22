package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class IcmpHealthChecked extends AbstractEvent {

    private Long id;

    public IcmpHealthChecked(HealthCheckLog aggregate) {
        super(aggregate);
    }

    public IcmpHealthChecked() {
        super();
    }
}
//>>> DDD / Domain Event
