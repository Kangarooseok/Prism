package prism.infra.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CctvRequest {
    private String ipAddress;
    private Double latitude;
    private Double longitude;
    private String locationName;
    private String locationAddress;
    private String hlsAddress;
}
