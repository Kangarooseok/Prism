package prism.external;

import java.util.Date;
import lombok.Data;

@Data
public class Issue {

    private Long id;
    private String cctvId;
    private String status;
    private String description;
    private Object healthCheckType;
    private Date createdAt;
    private Date updatedAt;
    private Date failureTime;
    private Date resolvedAt;
}
