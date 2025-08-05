package prism.domain.cctv.command;

import lombok.Getter;
import lombok.Setter;

// CCTV 등록 요청을 담는 커맨드 객체
@Setter
@Getter
public class RegisterCctvCommand {
    private String locationName;     // 설치 위치 이름
    private String locationAddress;  // 설치 위치 주소
    private String ipAddress;        // CCTV IP 주소
    private String hlsAddress;       // HLS 스트리밍 주소
    private Float longitude;         // 경도
    private Float latitude;          // 위도
    private String status;           // 상태 (ACTIVE, OFFLINE)
    private Long groupId;            // 소속 그룹 ID
}
