package kr.gringrape.hamp.application;

import kr.gringrape.hamp.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class MeetingService {

    private MeetingRepository meetingRepository;

    private UserRepository userRepository;

    @Autowired
    public void setMeetingRepository(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Meeting> getMeetings(
            Optional<String> address,
            Optional<Long> topicId,
            Optional<String> keyword,
            Optional<LocalDateTime> durationStart,
            Optional<LocalDateTime> durationEnd
    ) {

        List<Meeting> meetings = meetingRepository.findAll();

        List<Meeting> filtered =
                meetings.stream()
                        .filter(getAddressPredicate(address))
                        .filter(getTopicIdPredicate(topicId))
                        .filter(getKeywordPredicate(keyword))
                        .filter(getDurationStartPredicate(durationStart))
                        .filter(getDurationEndPredicate(durationEnd))
                        .collect(Collectors.toList());

        return filtered;

    }

    private Predicate<? super Meeting> getAddressPredicate(Optional<String> address) {
        return address.isPresent()
                ? (meeting) -> meeting.getAddress().contains(address.get())
                : (meeting) -> true;
    }

    private Predicate<? super Meeting> getTopicIdPredicate(Optional<Long> topicId) {
        return topicId.isPresent()
                ? (meeting) -> meeting.getTopicId()
                .equals(topicId.get())
                : (meeting) -> true;
    }

    private Predicate<? super Meeting> getKeywordPredicate(Optional<String> keyword) {
        return keyword.isPresent()
                ? (meeting) -> meeting.getTitle()
                .contains(keyword.get())
                : (meeting) -> true;
    }

    private Predicate<? super Meeting> getDurationStartPredicate(Optional<LocalDateTime> durationStart) {
        return durationStart.isPresent()
                ? (meeting) -> meeting.getStartDate()
                                .isAfter(durationStart.get())
                : (meeting) -> true;
    }

    private Predicate<? super Meeting> getDurationEndPredicate(Optional<LocalDateTime> durationEnd) {
        return durationEnd.isPresent()
                ? (meeting) -> meeting.getEndDate()
                .isBefore(durationEnd.get())
                : (meeting) -> true;
    }

    public Meeting getMeeting(Long id) {

        Meeting meeting = meetingRepository.findMeetingById(id)
                .orElseThrow(() -> new MeetingNotFoundException(id));

        return meeting;
    }

    public Meeting addMeeting(Meeting resource) {

        resource.initialize();

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

        List<User> applyingUsers = userRepository.findAllByAppliedMeetings(meeting);

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
