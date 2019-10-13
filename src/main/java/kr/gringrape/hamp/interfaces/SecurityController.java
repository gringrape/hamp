package kr.gringrape.hamp.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;

@Slf4j
@CrossOrigin
@RestController
public class SecurityController {

    @GetMapping("/security")
    public Principal security(HttpServletRequest request, Principal principal) {


        return principal;

    }
}
