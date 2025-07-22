package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class NetworkAlarmRaised extends AbstractEvent {

    private Long id;
    private String cctvId;
    private String issue;
    private Date raisedAt;

    public NetworkAlarmRaised(NetworkAction aggregate) {
        super(aggregate);
    }

    public NetworkAlarmRaised() {
        super();
    }
}
//>>> DDD / Domain Event
