package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class SituationResolved extends AbstractEvent {

    private Long id;

    public SituationResolved(TvResolution aggregate) {
        super(aggregate);
    }

    public SituationResolved() {
        super();
    }
}
//>>> DDD / Domain Event
