package prism.domain;

import java.util.*;
import lombok.*;
import prism.domain.cctv.model.Cctv;
import prism.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class CctvRegistered extends AbstractEvent {

    private Long id;
    private String locationName;
    private String locationAddress;
    private String ipAddress;
    private String hlsAddress;
    private Float longitude;
    private Float latitude;
    private Date createdAt;
    private Date updatedAt;
    private String status;

    public CctvRegistered(Cctv aggregate) {
        super(aggregate);
    }

    public CctvRegistered() {
        super();
    }
}
//>>> DDD / Domain Event
