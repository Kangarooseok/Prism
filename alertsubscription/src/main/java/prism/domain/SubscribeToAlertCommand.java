package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class SubscribeToAlertCommand {

    private String userId;
    private alertType alertType;
    private severityLevel severityLevel;
}
