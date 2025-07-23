package prism.domain.user.command;

import lombok.Data;

@Data
public class UserRegisterCommand {

    private String name;
    private String email;
    private String role;
    private String assignedTeam;
}
