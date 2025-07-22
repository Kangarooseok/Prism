package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class IssueCreated extends AbstractEvent {

    private Long id;
    private String cctvId;
    private healthCheckType healthCheckType;
    private Date createdAt;
    private Date failureTime;

    public IssueCreated(Issue aggregate) {
        super(aggregate);
    }

    public IssueCreated() {
        super();
    }
}
//>>> DDD / Domain Event
