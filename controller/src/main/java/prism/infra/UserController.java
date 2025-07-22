package prism.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prism.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/users")
@Transactional
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(
        value = "/users/{id}/userregister",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public User userRegister(
        @PathVariable(value = "id") Long id,
        @RequestBody UserRegisterCommand userRegisterCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /user/userRegister  called #####");
        Optional<User> optionalUser = userRepository.findById(id);

        optionalUser.orElseThrow(() -> new Exception("No Entity Found"));
        User user = optionalUser.get();
        user.userRegister(userRegisterCommand);

        userRepository.save(user);
        return user;
    }

    @RequestMapping(
        value = "/users/{id}/userupdate",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public User userUpdate(
        @PathVariable(value = "id") Long id,
        @RequestBody UserUpdateCommand userUpdateCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /user/userUpdate  called #####");
        Optional<User> optionalUser = userRepository.findById(id);

        optionalUser.orElseThrow(() -> new Exception("No Entity Found"));
        User user = optionalUser.get();
        user.userUpdate(userUpdateCommand);

        userRepository.save(user);
        return user;
    }

    @RequestMapping(
        value = "/users/{id}/userdelete",
        method = RequestMethod.DELETE,
        produces = "application/json;charset=UTF-8"
    )
    public User userDelete(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /user/userDelete  called #####");
        Optional<User> optionalUser = userRepository.findById(id);

        optionalUser.orElseThrow(() -> new Exception("No Entity Found"));
        User user = optionalUser.get();
        user.userDelete();

        userRepository.delete(user);
        return user;
    }

    @RequestMapping(
        value = "/users/assignusertoteam",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public User assignUserToTeam(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody AssignUserToTeamCommand assignUserToTeamCommand
    ) throws Exception {
        System.out.println("##### /user/assignUserToTeam  called #####");
        User user = new User();
        user.assignUserToTeam(assignUserToTeamCommand);
        userRepository.save(user);
        return user;
    }
}
//>>> Clean Arch / Inbound Adaptor
