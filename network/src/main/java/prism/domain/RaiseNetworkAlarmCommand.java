package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RaiseNetworkAlarmCommand {

    private String cctvId;
    private actionType actionType;
    private String performedBy;
}
