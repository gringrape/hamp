package kr.gringrape.hamp.application;

import kr.gringrape.hamp.domain.Topic;
import kr.gringrape.hamp.infrastructure.persistence.TopicRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class TopicServiceTests {

    TopicService topicService;

    @Mock
    TopicRepository topicRepository;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        this.topicService = new TopicService(topicRepository);

    }

    @Test
    public void getTopics() {

        List<Topic> mockTopics = new ArrayList<>();

        mockTopics.add(Topic.builder().name("C 언어").build());

        given(topicRepository.findAll())
                .willReturn(mockTopics);

       List<Topic> topics = topicService.getTopics();

       verify(topicRepository).findAll();

        Topic topic = topics.get(0);

        assertThat(topic.getName(), is("C 언어"));

    }

    @Test
    public void addTopic() {

        Topic resource = Topic.builder().name("C 언어").build();

        Topic mockTopic = Topic.builder().name("C 언어").id(1L).build();
        given(topicRepository.save(any()))
                .willReturn(mockTopic);

        Topic topic = topicService.addTopic(resource);

        verify(topicRepository).save(any());

        assertThat(topic.getName(),is("C 언어"));

    }

}