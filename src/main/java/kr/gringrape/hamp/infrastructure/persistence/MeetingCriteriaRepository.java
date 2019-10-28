package kr.gringrape.hamp.infrastructure.persistence;

import kr.gringrape.hamp.domain.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MeetingCriteriaRepository {

    @Autowired
    MeetingRepository meetingRepository;

    public Page findByCriteria(
            String address,
            String keyword,
            Long topicId,
            LocalDateTime durationStart,
            LocalDateTime durationEnd,
            Pageable pageable
    ) {
        Page page = meetingRepository.findAll(
                (Root<Meeting> root,
                 CriteriaQuery<?> query,
                 CriteriaBuilder criteriaBuilder) ->
                {
                    List<Predicate> predicates = new ArrayList<>();
                    if (address != null) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("address"), "%" + address + "%")));
                    }
                    if (keyword != null) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("title"), "%" + keyword + "%")));
                    }
                    if (topicId != null) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("topicId"), topicId)));
                    }
                    if (durationStart != null) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThan(root.get("startDate"), durationStart)));
                    }
                    if (durationEnd != null) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.lessThan((root.get("endDate")), durationEnd)));
                    }

                    query.orderBy(criteriaBuilder.desc(root.get("startDate")));

                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }, pageable);

        return page;
    }
}
