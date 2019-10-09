package kr.gringrape.hamp.application;

import kr.gringrape.hamp.domain.Meeting;
import kr.gringrape.hamp.domain.MeetingNotFoundException;
import kr.gringrape.hamp.domain.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeetingService {

    private MeetingRepository meetingRepository;

    @Autowired
    public void setMeetingRepository(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public List<Meeting> getMeetings(String address) {

        List<Meeting> meetings = meetingRepository
                .findAllByAddressContaining(address);

        return meetings;

    }

    public List<Meeting> getMeetings(
            String address, Long topicId
    ) {

        if(topicId == null) {
            return getMeetings(address);
        }

        List<Meeting> meetings = meetingRepository
                .findAllByAddressContainingAndTopicId(address, topicId);

        return meetings;

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

}
