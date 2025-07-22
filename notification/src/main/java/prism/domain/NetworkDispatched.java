package prism.domain;

import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

@Data
@ToString
public class NetworkDispatched extends AbstractEvent {

    private Long id;
    private String cctvId;
    private Date dispatchedAt;
    private Date dispatchedBy;
}
