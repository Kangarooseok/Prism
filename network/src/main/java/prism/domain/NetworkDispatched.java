package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class NetworkDispatched extends AbstractEvent {

    private Long id;
    private String cctvId;
    private Date dispatchedAt;
    private Date dispatchedBy;

    public NetworkDispatched(NetworkAction aggregate) {
        super(aggregate);
    }

    public NetworkDispatched() {
        super();
    }
}
//>>> DDD / Domain Event
