package kr.gringrape.hamp.application;

import kr.gringrape.hamp.domain.Meeting;
import kr.gringrape.hamp.domain.MeetingNotFoundException;
import kr.gringrape.hamp.domain.MeetingRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class MeetingServiceTests {

    MeetingService meetingService;

    @Mock
    MeetingRepository meetingRepository;

    /* fixture */
    Meeting resource;
    Meeting meeting;
    List<Meeting> meetings;

    @Before
    public void setup() {

        meetingService = new MeetingService();
        MockitoAnnotations.initMocks(this);
        meetingService.setMeetingRepository(meetingRepository);

        resource = Meeting.builder()
            .topicId(1L)
            .title("함께 C언어 공부해요~!")
            .description("초보자도 쉽게 할 수 있어요~!")
            .build();

        meeting = Meeting.builder()
            .id(1004L)
            .topicId(2L)
            .title("함께 python 공부해요~!")
            .description("누구나 쉽게 할 수 있어요~!")
            .build();

        meetings = new ArrayList<>();
        meetings.add(meeting);
    }

    @Test
    public void getMeetings() {
        given(meetingRepository.findAllByAddressContaining(""))
                .willReturn(meetings);

        List<Meeting> theMeetings = meetingService.getMeetings("");

        verify(meetingRepository)
                .findAllByAddressContaining("");

        assertThat(theMeetings.get(0).getTopicId(), is(2L));
    }

    @Test
    public void getMeetingsByAddress() {
        List<Meeting> mockMeetings = new ArrayList<>();

        mockMeetings.add(Meeting.builder()
                .title("서울 개발 모임")
                .build());

        given(meetingRepository
                .findAllByAddressContaining(eq("서울")))
                .willReturn(mockMeetings);

        List<Meeting> meetings = meetingService.getMeetings("서울");

        verify(meetingRepository).findAllByAddressContaining(eq("서울"));

        Meeting meeting = meetings.get(0);

        assertThat(meeting.getTitle(), is("서울 개발 모임"));
    }

    @Test
    public void getMeetingsByTopic() {
        List<Meeting> mockMeetings = new ArrayList<>();

        mockMeetings.add(Meeting.builder()
                .title("서울 개발 모임")
                .build());

        given(meetingRepository
                .findAllByAddressContainingAndTopicId(eq(""), eq(1L)))
                .willReturn(mockMeetings);

        List<Meeting> meetings = meetingService.getMeetings("",1L);

        verify(meetingRepository)
                .findAllByAddressContainingAndTopicId(eq(""), eq(1L));

        Meeting meeting = meetings.get(0);

        assertThat(meeting.getTitle(), is("서울 개발 모임"));
    }

    @Test
    public void getMeetingsByAddressAndTopic() {
        List<Meeting> mockMeetings = new ArrayList<>();

        mockMeetings.add(Meeting.builder()
                .title("서울 개발 모임")
                .build());

        given(meetingRepository
                .findAllByAddressContainingAndTopicId(eq("서울"),eq(1L)))
                .willReturn(mockMeetings);

        List<Meeting> meetings = meetingService.getMeetings("서울",1L);

        verify(meetingRepository)
                .findAllByAddressContainingAndTopicId(eq("서울"), eq(1L));

        Meeting meeting = meetings.get(0);

        assertThat(meeting.getTitle(), is("서울 개발 모임"));
    }

    @Test
    public void getMeetingWithExistingId() {
        given(meetingRepository.findMeetingById(1L))
                .willReturn(Optional.ofNullable(meeting));

       Meeting theMeeting = meetingService.getMeeting(1L);

       verify(meetingRepository).findMeetingById(eq(1L));

       assertThat(theMeeting.getTopicId(), is(2L));
    }

    @Test(expected = MeetingNotFoundException.class)
    public void getMeetingWithNotExistingId() {
        given(meetingRepository.findMeetingById(eq(404L)))
                .willReturn(Optional.empty());

        meetingService.getMeeting(404L);
    }

    @Test
    public void addMeeting() {
        resource.setId(1L);
        given(meetingRepository.save(any())).willReturn(resource);

        Meeting meeting = meetingService.addMeeting(resource);

        verify(meetingRepository).save(any());
        assertInitialized(meeting);
    }

    private void assertInitialized(Meeting meeting) {
        assertNotNull(meeting.getMeetDate());
        assertThat(meeting.getNoAppliers(), is(1));
    }

    @Test
    public void modifyMeeting() {
        Meeting meeting = Meeting.builder()
                .id(1L)
                .topicId(3L)
                .title("족구를 배우자~")
                .description("족구를 체계적으로 배워보는 곳입니다~ 환영합니다~")
                .build();

        given(meetingRepository.findMeetingById(eq(1L))).willReturn(java.util.Optional.ofNullable(meeting));

        Meeting resource = Meeting.builder()
                .topicId(1L)
                .title("C 언어를 함께 공부합시다~")
                .description("C 언어를 함께 공부하는 모임이에요~ 오셔서 구경해보세요~")
                .build();

        Meeting modified = meetingService.modifyMeeting(1L, resource);

        verify(meetingRepository).findMeetingById(1L);

        verify(meetingRepository).save(any());

        assertThat(modified.getTopicId(), is(1L));
    }

    @Test
    public void removeMeetingWithExistingId() {
        Meeting meeting = Meeting.builder().build();

        given(meetingRepository.findMeetingById(eq(1L)))
                .willReturn(Optional.ofNullable(meeting));

        meetingService.removeMeeting(1L);

        verify(meetingRepository).deleteById(1L);
    }

    @Test(expected = MeetingNotFoundException.class)
    public void removeMeetingWithNotExistingId() {

        given(meetingRepository.findMeetingById(eq(404L)))
                .willReturn(Optional.empty());

        meetingService.removeMeeting(404L);
    }
}