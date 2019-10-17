package kr.gringrape.hamp.interfaces;

import kr.gringrape.hamp.application.MeetingService;
import kr.gringrape.hamp.domain.Meeting;
import kr.gringrape.hamp.interfaces.dto.FilterParameters;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @GetMapping("/meetings")
    public List<Meeting> list(
            @RequestParam(value = "address", required = false) Optional<String> address,
            @RequestParam(value = "topicId", required = false) Optional<Long> topicId,
            @RequestParam(value = "keyword", required = false) Optional<String> keyword,
            @RequestParam(value = "durationStart", required = false) Optional<LocalDateTime> durationStart,
            @RequestParam(value = "durationEnd", required = false) Optional<LocalDateTime> durationEnd

    ) {
        // TODO 날짜를 통한 필터링을 할 수 있도록 기간의 시작점과 끝점을 얻어오기

        List<Meeting> meetings = meetingService
                .getMeetings(
                        address, topicId, keyword, durationStart, durationEnd
                );

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
            @Valid @RequestBody Meeting resource
    ) {

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
