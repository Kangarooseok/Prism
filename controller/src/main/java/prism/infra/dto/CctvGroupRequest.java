package prism.infra.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

// CCTV 그룹 생성/수정 요청 DTO
@Getter
@Setter
public class CctvGroupRequest {
    private String name;              // 그룹 이름
    private String description;       // 그룹 설명
    private List<Long> cctvIds;       // 그룹에 포함할 CCTV ID 목록
}
