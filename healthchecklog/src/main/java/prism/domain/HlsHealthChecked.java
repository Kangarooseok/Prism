package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class HlsHealthChecked extends AbstractEvent {

    private Long id;

    public HlsHealthChecked(HealthCheckLog aggregate) {
        super(aggregate);
    }

    public HlsHealthChecked() {
        super();
    }
}
//>>> DDD / Domain Event
