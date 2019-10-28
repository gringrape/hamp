package kr.gringrape.hamp.application;

import kr.gringrape.hamp.application.exceptions.UserNotFoundException;
import kr.gringrape.hamp.domain.*;
import kr.gringrape.hamp.application.exceptions.MeetingNotFoundException;
import kr.gringrape.hamp.infrastructure.persistence.MeetingCriteriaRepository;
import kr.gringrape.hamp.infrastructure.persistence.MeetingRepository;
import kr.gringrape.hamp.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeetingService {

    private MeetingRepository meetingRepository;
    private MeetingCriteriaRepository meetingCriteriaRepository;
    private UserRepository userRepository;

    @Autowired
    public void setMeetingRepository(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setMeetingCriteriaRepository(MeetingCriteriaRepository meetingCriteriaRepository) {
        this.meetingCriteriaRepository = meetingCriteriaRepository;
    }

    public Page getPage(
            String address,
            Long topicId,
            String keyword,
            LocalDateTime durationStart,
            LocalDateTime durationEnd,
            Integer pageNum
    ) {

        Page page = meetingCriteriaRepository.findByCriteria(
                address, keyword, topicId, durationStart, durationEnd,
                PageRequest.of(pageNum - 1, 8));

        return page;

    }

    public Meeting getMeeting(Long id) {

        Meeting meeting = meetingRepository.findMeetingById(id)
                .orElseThrow(() -> new MeetingNotFoundException(id));

        return meeting;
    }

    public Meeting addMeeting(Meeting resource, Long writerId) {

        resource.initialize();

        User writer = userRepository.findById(writerId)
                .orElseThrow(() -> new UserNotFoundException(writerId));

        resource.setWriter(writer);

        Meeting meeting = meetingRepository.save(resource);

        return meeting;
    }

    public void removeMeeting(Long id) {

        meetingRepository.findMeetingById(id)
                .orElseThrow(() -> new MeetingNotFoundException(id));

        meetingRepository.deleteById(id);

    }

    public Meeting modifyMeeting(Long id, Meeting resource) {

        Meeting meeting = meetingRepository.findMeetingById(id)
                .orElseThrow(() -> new MeetingNotFoundException(id));

        updateWithResource(meeting, resource);

        meetingRepository.save(meeting);

        return meeting;
    }

    private void updateWithResource(Meeting meeting, Meeting resource) {

        meeting.setDescription(resource.getDescription());
        meeting.setTitle(resource.getTitle());
        meeting.setTopicId(resource.getTopicId());

    }

    public List<User> getApplyingUsers(Long meetingId) {

        Meeting meeting =
                meetingRepository.findMeetingById(meetingId)
                        .orElseThrow(() -> new MeetingNotFoundException(meetingId));

        List<User> applyingUsers = meeting.getApplyingUsers();

        return applyingUsers;

    }

    public void addUserToList(Long meetingId, Long userId) {
        User user = userRepository.findById(userId)
                .orElse(null);

        Meeting meeting = meetingRepository.findMeetingById(meetingId)
                .orElseThrow(() -> new MeetingNotFoundException(meetingId));

        meeting.addUser(user);

        meetingRepository.save(meeting);
    }
}
