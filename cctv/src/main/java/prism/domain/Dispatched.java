package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class Dispatched extends AbstractEvent {

    private Long id;
    private String cctvId;
    private String status;
    private String healthCheckId;

    public Dispatched(TvResolution aggregate) {
        super(aggregate);
    }

    public Dispatched() {
        super();
    }
}
//>>> DDD / Domain Event
