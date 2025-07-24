package prism.infra.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CctvRequest {
    private String locationName;
    private String locationAddress;
    private String ipAddress;
    private String hlsAddress;
    private Float longitude;
    private Float latitude;
    private String status;
}
