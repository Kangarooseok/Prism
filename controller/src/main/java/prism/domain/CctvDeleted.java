package prism.domain;

import java.util.*;
import lombok.*;
import prism.domain.cctv.model.Cctv;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
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

    public CctvDeleted(Cctv aggregate) {
        super(aggregate);
    }

    public CctvDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
