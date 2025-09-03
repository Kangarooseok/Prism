package prism.infra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import prism.domain.user.dto.UserDto;
import prism.domain.user.repository.UserReadDao;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UserReadDao userReadDao;

    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return userReadDao.findAllActive();
    }
}
