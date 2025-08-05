package prism.domain.group.command;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegisterCctvGroupCommand {
    private String name;
    private String description;
    private List<Long> cctvIds;       // 그룹에 포함될 CCTV ID 목록

}

