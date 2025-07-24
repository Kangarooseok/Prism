package prism.domain.cctv.event;

import java.util.*;
import lombok.*;
import prism.domain.cctv.model.Cctv;
import prism.infra.AbstractEvent;

@Setter
@Getter
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

    public CctvDeleted(Cctv cctv) {
        super(cctv, "prism.cctv"); // ✅ topic 설정
    }
}

