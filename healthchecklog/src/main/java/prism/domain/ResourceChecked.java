package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class ResourceChecked extends AbstractEvent {

    private Long id;

    public ResourceChecked(HealthCheckLog aggregate) {
        super(aggregate);
    }

    public ResourceChecked() {
        super();
    }
}
//>>> DDD / Domain Event
