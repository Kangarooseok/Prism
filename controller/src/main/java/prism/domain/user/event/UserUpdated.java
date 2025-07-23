package prism.domain.user.event;

import java.util.*;
import lombok.*;
import prism.domain.user.model.User;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class UserUpdated extends AbstractEvent {

    private Long id;
    private String name;
    private String email;
    private Date updatedAt;

    public UserUpdated(User aggregate) {
        super(aggregate);
    }

    public UserUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
