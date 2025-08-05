package prism.infra.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

// CCTV 그룹 조회 응답 DTO
@Getter
@Setter
public class CctvGroupResponse {
    private Long id;                  // 그룹 ID
    private String name;              // 그룹 이름
    private String description;       // 그룹 설명
    private List<Long> cctvIds;       // 포함된 CCTV ID 목록
}
