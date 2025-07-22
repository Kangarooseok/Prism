package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class UserAssignedToTeam extends AbstractEvent {

    private Long id;
    private String team;
    private String assignedAt;

    public UserAssignedToTeam(User aggregate) {
        super(aggregate);
    }

    public UserAssignedToTeam() {
        super();
    }
}
//>>> DDD / Domain Event
