package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class NetworkIssueResolved extends AbstractEvent {

    private Long id;
    private String cctvId;
    private Date resolvedAt;
    private Date resolvedBy;

    public NetworkIssueResolved(NetworkAction aggregate) {
        super(aggregate);
    }

    public NetworkIssueResolved() {
        super();
    }
}
//>>> DDD / Domain Event
