package kr.gringrape.hamp.application;

import kr.gringrape.hamp.domain.Topic;
import kr.gringrape.hamp.infrastructure.persistence.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {

        this.topicRepository = topicRepository;
    }

    public List<Topic> getTopics() {

        List<Topic> topics = topicRepository.findAll();

        return topics;

    }

    public Topic addTopic(Topic resource) {

        Topic topic = topicRepository.save(resource);

        return topic;

    }
}
