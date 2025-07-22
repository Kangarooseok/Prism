package prism.domain;

import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

@Data
@ToString
public class IssueStatusUpdated extends AbstractEvent {

    private Long id;
    private String status;
    private Date updatedAt;
}
