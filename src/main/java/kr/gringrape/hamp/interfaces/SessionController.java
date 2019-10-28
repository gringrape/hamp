package kr.gringrape.hamp.interfaces;

import io.jsonwebtoken.Claims;
import kr.gringrape.hamp.application.UserService;
import kr.gringrape.hamp.domain.User;
import kr.gringrape.hamp.interfaces.dtos.SessionRequestDto;
import kr.gringrape.hamp.interfaces.dtos.SessionResponseDto;
import kr.gringrape.hamp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/session")
    public ResponseEntity<?> userInfo(
            Authentication authentication
    ) {

        // TODO: spring security 를 사용하여 인증정보가 존재하지 않는 경우에 대한 예외처리(인가)

        Claims claims = Claims.class
                .cast(authentication.getPrincipal());

        Long userId = claims.get("userId", Long.class);

        return ResponseEntity
                .status(HttpStatus.SEE_OTHER)
                .header("Location", "/hamp-0.0.1-SNAPSHOT/users/" + userId)
                .body("{}");
    }

    @PostMapping("/session")
    public ResponseEntity<?> create(
            @RequestBody SessionRequestDto resource,
            HttpServletResponse response
    ) throws URISyntaxException { // session dtos 의 body 를 정해주는 기능.

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
