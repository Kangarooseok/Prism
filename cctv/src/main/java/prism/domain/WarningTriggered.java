package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class WarningTriggered extends AbstractEvent {

    private Long id;

    public WarningTriggered(TvResolution aggregate) {
        super(aggregate);
    }

    public WarningTriggered() {
        super();
    }
}
//>>> DDD / Domain Event
