package prism.domain;

import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

@Data
@ToString
public class NetworkAlarmRaised extends AbstractEvent {

    private Long id;
    private String cctvId;
    private String issue;
    private Date raisedAt;
}
