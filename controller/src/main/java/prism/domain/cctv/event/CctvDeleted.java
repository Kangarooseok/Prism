package prism.domain.cctv.event;

import java.util.*;
import lombok.*;
import prism.domain.cctv.model.Cctv;
import prism.infra.AbstractEvent;

// CCTV 삭제 이벤트 객체
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

    // 삭제된 CCTV 정보로 이벤트 초기화
    public CctvDeleted(Cctv cctv) {
        super(cctv, "prism.cctv");
    }
}
