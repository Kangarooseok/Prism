package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class DispatchCommand {

    private String cctvId;
    private String healthCheckId;
}
