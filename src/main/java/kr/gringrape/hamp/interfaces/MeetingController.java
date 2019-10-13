package kr.gringrape.hamp.interfaces;

import kr.gringrape.hamp.application.MeetingService;
import kr.gringrape.hamp.domain.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @GetMapping("/meetings")
    public List<Meeting> list(
            @RequestParam(name = "address", required = false)
                    Optional<String> address,
            @RequestParam(name = "topic", required = false)
                    Long topicId
    ) {

        List<Meeting> meetings = meetingService
                .getMeetings(address.orElse(""), topicId);

        return meetings;

    }

    @GetMapping("/meetings/{id}")
    public Meeting detail(
            @PathVariable("id") Long id
    ) {
        Meeting meeting = meetingService.getMeeting(id);
        return meeting;
    }

    @PostMapping("/meetings")
    public ResponseEntity<?> create(
            @Valid @RequestBody Meeting resource
    ) throws URISyntaxException {

        Meeting meeting = meetingService.addMeeting(resource);
        URI location = new URI("/meetings/" + meeting.getId());

        return ResponseEntity.created(location).body("{}");
    }

    @PatchMapping("/meetings/{id}")
    public String modify(
            Principal principal,
            @PathVariable("id") Long id,
            @Valid @RequestBody Meeting resource,
            @RequestParam(name = "apply", required = false) Optional<String> isApplying
    ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        auth.

        if(isApplying.isPresent()) {
            meetingService.addUserToMeeting(id, resource, principal.getName())
        }

        meetingService.modifyMeeting(id, resource);
        return "{}";
    }

    @DeleteMapping("/meetings/{id}")
    public ResponseEntity<?> remove(
            @PathVariable("id") Long id
    ) {
        meetingService.removeMeeting(id);

        return ResponseEntity.noContent().build();
    }

}
