package kr.gringrape.hamp.domain;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MeetingRepository extends CrudRepository<Meeting, Long>, JpaSpecificationExecutor<Meeting> {

    List<Meeting> findAll();

    Optional<Meeting> findMeetingById(Long id);

    Meeting save(Meeting resource);

    void deleteById(Long id);

}
