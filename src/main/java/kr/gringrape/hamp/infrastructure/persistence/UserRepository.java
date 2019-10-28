package kr.gringrape.hamp.infrastructure.persistence;

import kr.gringrape.hamp.domain.Meeting;
import kr.gringrape.hamp.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll();

    User save(User resource);

    Optional<User> findById(Long userId);

    Optional<User> findByEmail(String email);

    List<User> findAllByAppliedMeetings(Meeting meeting);
}
