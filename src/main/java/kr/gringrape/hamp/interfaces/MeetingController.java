package kr.gringrape.hamp.interfaces;

import io.jsonwebtoken.Claims;
import kr.gringrape.hamp.application.MeetingService;
import kr.gringrape.hamp.domain.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @GetMapping("/meetings")
    public List<Meeting> list(
            Meeting resource,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            HttpServletResponse response

    ) {
        // TODO 날짜를 통한 필터링을 할 수 있도록 기간의 시작점과 끝점을 얻어오기

        Page page = meetingService
                .getPage(
                        resource.getAddress(),
                        resource.getTopicId(),
                        keyword,
                        resource.getStartDate(),
                        resource.getEndDate(),
                        pageNum
                );

        response.setHeader("totalPages", "" + page.getTotalPages());
        response.setHeader(
                "Access-Control-Expose-Headers",
                "totalPages"
        );

        return page.getContent();

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
            @Valid @RequestBody Meeting resource,
            Authentication authentication
    ) throws URISyntaxException {

        Claims claims = Claims.class
                .cast(authentication.getPrincipal());

        Long writerId = claims.get("userId", Long.class);

        Meeting meeting = meetingService.addMeeting(resource, writerId);
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
