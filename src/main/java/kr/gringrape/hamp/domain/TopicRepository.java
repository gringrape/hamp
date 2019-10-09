package kr.gringrape.hamp.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TopicRepository extends CrudRepository<Topic, Long> {

    List<Topic> findAll();

    Topic save(Topic resource);

}
