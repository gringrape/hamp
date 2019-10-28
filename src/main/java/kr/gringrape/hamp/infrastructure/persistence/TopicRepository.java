package kr.gringrape.hamp.infrastructure.persistence;

import kr.gringrape.hamp.domain.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TopicRepository extends CrudRepository<Topic, Long> {

    List<Topic> findAll();

    Topic save(Topic resource);

}
