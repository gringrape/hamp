package kr.gringrape.hamp.interfaces;

import kr.gringrape.hamp.application.UserService;
import kr.gringrape.hamp.domain.User;
import kr.gringrape.hamp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@CrossOrigin
@RestController
public class SessionController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/session")
    public ResponseEntity<?> create(
            @RequestBody SessionRequestDto resource,
            HttpServletResponse response
    ) throws URISyntaxException { // session dto 의 body 를 정해주는 기능.

        String email = resource.getEmail();
        String password = resource.getPassword();

        User user = userService.authenticate(email, password);

        SessionResponseDto sessionResponseDto =
                SessionResponseDto
                        .builder()
                        .accessToken(
                                jwtUtil.createToken(
                                        user.getId(),
                                        user.getNick()
                                ))
                        .build();

        String url = "/session";

        return ResponseEntity
                .created(new URI(url))
                .header(
                        "accessToken",
                        sessionResponseDto.getAccessToken()
                )
                .header(
                        "Access-Control-Expose-Headers",
                        "accessToken"
                )
                .body("{}");

    }
}
