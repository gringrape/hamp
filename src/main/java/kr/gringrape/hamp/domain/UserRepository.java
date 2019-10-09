package kr.gringrape.hamp.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll();

    User save(User resource);

    Optional<User> findByEmail(String email);

}
