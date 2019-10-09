package kr.gringrape.hamp.interfaces;

import kr.gringrape.hamp.application.TopicService;
import kr.gringrape.hamp.domain.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/topics")
    public List<Topic> list() {

        List<Topic> topics = topicService.getTopics();

        return topics;

    }

    @PostMapping("/topics")
    public ResponseEntity<?> create(
            @RequestBody Topic resource
    ) throws URISyntaxException {

        Topic topic = topicService.addTopic(resource);

        String url = "/topics/1";

        return ResponseEntity.created(new URI(url)).body("{}");

    }

}
