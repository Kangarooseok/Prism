package prism.domain.user.event;

import lombok.*;
import prism.domain.user.model.User;
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
