package prism.domain;

import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

@Data
@ToString
public class NotificationSent extends AbstractEvent {

    private Long id;
    private String receiver;
    private String message;
    private String status;
    private Date sentAt;
    private String healthCheckId;
}
