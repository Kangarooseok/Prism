package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class CreateNotificationCommand {

    private String receiver;
    private String message;
    private String status;
    private String healthCheckId;
}
