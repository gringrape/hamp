package kr.gringrape.hamp.interfaces;

import kr.gringrape.hamp.application.UserService;
import kr.gringrape.hamp.domain.User;
import kr.gringrape.hamp.interfaces.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users/{userId}")
    public UserDto detail(
            @PathVariable Long userId
    ) {

        User user = userService.getUser(userId);

        UserDto userDto = UserDto.builder()
                .nick(user.getNick())
                .email(user.getEmail())
                .level(user.getLevel())
                .appliedMeetings(user.getAppliedMeetings())
                .build();

        return userDto;

    }

    @PostMapping("/users")
    public ResponseEntity<?> create(
            @RequestBody User resource
    ) throws URISyntaxException {

        String email = resource.getEmail();
        String nick = resource.getNick();
        String password = resource.getPassword();

        User user = userService.registerUser(email, nick, password);

        String url = "/users/" + user.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }

    @PatchMapping("/users/{userId}")
    public String modify(
            @PathVariable Long userId,
            @RequestBody User resource
    ) {

        User user = userService.updateUser(
                userId,
                resource.getEmail(),
                resource.getPassword(),
                resource.getNick()
        );

        return "{}";
    }

}
