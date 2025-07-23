package prism.domain.user.model;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import prism.ControllerApplication;
import prism.domain.user.command.AssignUserToTeamCommand;
import prism.domain.user.command.UserRegisterCommand;
import prism.domain.user.command.UserUpdateCommand;
import prism.domain.user.event.UserAssignedToTeam;
import prism.domain.user.event.UserDeleted;
import prism.domain.user.event.UserRegistered;
import prism.domain.user.event.UserUpdated;
import prism.domain.user.repository.UserRepository;

@Entity
@Table(name = "User_table")
@Data
//<<< DDD / Aggregate Root.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    private String role;

    private String assignedTeam;

    private Date createdAt;

    private Date updatedAt;

    @PostPersist
    public void onPostPersist() {
        UserDeleted userDeleted = new UserDeleted(this);
        userDeleted.publishAfterCommit();

        UserRegistered userRegistered = new UserRegistered(this);
        userRegistered.publishAfterCommit();

        UserUpdated userUpdated = new UserUpdated(this);
        userUpdated.publishAfterCommit();

        UserAssignedToTeam userAssignedToTeam = new UserAssignedToTeam(this);
        userAssignedToTeam.publishAfterCommit();
    }

    public static UserRepository repository() {
        UserRepository userRepository = ControllerApplication.applicationContext.getBean(
            UserRepository.class
        );
        return userRepository;
    }

    //<<< Clean Arch / Port Method
    public void userRegister(UserRegisterCommand userRegisterCommand) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void userUpdate(UserUpdateCommand userUpdateCommand) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void userDelete() {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void assignUserToTeam(
        AssignUserToTeamCommand assignUserToTeamCommand
    ) {
        //implement business logic here:

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
