package prism.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class UserRegisterCommand {

    private String name;
    private String email;
    private String role;
    private String assignedTeam;
}
