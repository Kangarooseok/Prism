package prism.domain.user.event;

import lombok.*;
import prism.domain.user.model.User;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class UserDeleted extends AbstractEvent {

    private Long id;
    private String name;

    public UserDeleted(User aggregate) {
        super(aggregate);
    }

    public UserDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
