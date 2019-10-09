package kr.gringrape.hamp.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends CrudRepository<Meeting, Long> {

    List<Meeting> findAll();

    List<Meeting> findAllByAddressContaining(String address);

    List<Meeting> findAllByAddressContainingAndTopicId(String address, Long topicId);

    List<Meeting> findAllByTopicId(Long topicId);

    Optional<Meeting> findMeetingById(Long id);

    Meeting save(Meeting resource);

    void deleteById(Long id);

}
