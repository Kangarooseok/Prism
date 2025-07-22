package prism.domain;

import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

@Data
@ToString
public class IssueCreated extends AbstractEvent {

    private Long id;
    private String cctvId;
    private Object healthCheckType;
    private Date createdAt;
    private Date failureTime;
}
