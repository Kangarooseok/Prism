package prism.infra.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CctvGroupResponse {
    private Long id;
    private String name;
    private String description;
    private List<Long> cctvIds;

    private Date createdAt;
}
