package prism.domain.cctv.event;

import java.util.*;
import lombok.*;
import prism.domain.cctv.model.Cctv;
import prism.infra.AbstractEvent;

// CCTV 수정 이벤트 객체
@Setter
@Getter
@ToString
public class CctvModified extends AbstractEvent {

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

    // 수정된 CCTV 정보로 이벤트 초기화
    public CctvModified(Cctv cctv) {
        super(cctv, "prism.cctv");
    }
}
