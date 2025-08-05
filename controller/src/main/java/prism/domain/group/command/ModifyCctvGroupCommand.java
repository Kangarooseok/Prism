package prism.domain.group.command;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

// CCTV 그룹 수정 요청을 담는 커맨드 객체
@Getter
@Setter
public class ModifyCctvGroupCommand {
    private String name;              // 그룹 이름
    private String description;       // 그룹 설명
    private List<Long> cctvIds;       // 그룹에 포함될 CCTV ID 목록
}
