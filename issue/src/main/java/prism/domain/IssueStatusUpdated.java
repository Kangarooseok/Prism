package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class IssueStatusUpdated extends AbstractEvent {

    private Long id;
    private String status;
    private Date updatedAt;

    public IssueStatusUpdated(Issue aggregate) {
        super(aggregate);
    }

    public IssueStatusUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
