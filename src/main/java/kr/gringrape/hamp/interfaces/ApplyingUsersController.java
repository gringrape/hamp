package kr.gringrape.hamp.interfaces;

import io.jsonwebtoken.Claims;
import kr.gringrape.hamp.application.MeetingService;
import kr.gringrape.hamp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
public class ApplyingUsersController {

    @Autowired
    MeetingService meetingService;

    @GetMapping("/meetings/{meetingId}/applyingUsers")
    public List<User> list(
            @PathVariable("meetingId") Long meetingId
    ) {

        List<User> applyingUsersList = meetingService.getApplyingUsers(meetingId);

        return applyingUsersList;

    }

    @PostMapping("/meetings/{meetingId}/applyingUsers")
    public ResponseEntity<?> addUser(
            @PathVariable("meetingId") Long meetingId,
            Authentication authentication
    ) throws URISyntaxException {

        Claims claims = Claims.class
                .cast(authentication.getPrincipal());

        Long userId = claims.get("userId", Long.class);

        meetingService.addUserToList(
                meetingId,
                userId
        );

        String url = "/meetings/" + meetingId + "/applyingUsers/" + userId;

        return ResponseEntity.created(new URI(url)).body("{}");

    }
}
