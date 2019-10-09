package kr.gringrape.hamp.interfaces;

import kr.gringrape.hamp.application.TopicService;
import kr.gringrape.hamp.domain.Topic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TopicController.class)
public class TopicControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    TopicService topicService;

    @Test
    public void list() throws Exception {

        List<Topic> topics = new ArrayList<>();

        topics.add(Topic.builder().name("C 언어").build());

        given(topicService.getTopics())
                .willReturn(topics);

       mvc.perform(get("/topics"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString("\"name\":\"C 언어\"")));

        verify(topicService).getTopics();

    }

    @Test
    public void create() throws Exception {

        mvc.perform(post("/topics")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"C 언어\"}"))
                .andExpect(status().isCreated());

        verify(topicService).addTopic(any());

    }

}