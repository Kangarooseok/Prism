package prism.domain.cctv.command;

import lombok.Data;

@Data
public class ModifyCctvCommand {

    private String locationName;
    private String locationAddress;
    private String ipAddress;
    private String hlsAddress;
    private Float longitude;
    private Float latitude;
    private String status;
}
