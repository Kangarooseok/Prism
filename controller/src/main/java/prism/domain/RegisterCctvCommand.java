package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RegisterCctvCommand {

    private String locationName;
    private String locationAddress;
    private String ipAddress;
    private String hlsAddress;
    private Float longitude;
    private Float latitude;
    private String status;
}
