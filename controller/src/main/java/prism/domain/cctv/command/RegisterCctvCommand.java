package prism.domain.cctv.command;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterCctvCommand {
    private String locationName;
    private String locationAddress;
    private String ipAddress;
    private String hlsAddress;
    private Float longitude;
    private Float latitude;
    private String status;
}
