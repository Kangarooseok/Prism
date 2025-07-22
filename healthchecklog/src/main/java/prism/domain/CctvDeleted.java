package prism.domain;

import java.util.*;
import lombok.*;
import prism.domain.*;
import prism.infra.AbstractEvent;

@Data
@ToString
public class CctvDeleted extends AbstractEvent {

    private Long id;
    private String locationName;
    private String locationAddress;
    private String ipAddress;
    private String hlsAddress;
    private Date createdAt;
    private Date updatedAt;
    private String status;
}
