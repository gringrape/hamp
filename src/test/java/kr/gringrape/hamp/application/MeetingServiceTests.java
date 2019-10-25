package kr.gringrape.hamp.application;

import kr.gringrape.hamp.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class MeetingServiceTests {

    MeetingService meetingService;

    @Mock
    MeetingRepository meetingRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    MeetingCriteriaRepository meetingCriteriaRepository;

    /* fixture */
    Meeting resource;
    Meeting meeting;
    List<Meeting> meetings;
    List<Meeting> mockMeetingsForFilter;


    @Before
    public void setup() {

        meetingService = new MeetingService();
        MockitoAnnotations.initMocks(this);
        meetingService.setMeetingRepository(meetingRepository);
        meetingService.setUserRepository(userRepository);
        meetingService.setMeetingCriteriaRepository(meetingCriteriaRepository);

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

    @Test(expected = MeetingNotFoundException.class)
    public void getMeetingWithNotExistingId() {
        given(meetingRepository.findMeetingById(eq(404L)))
                .willReturn(Optional.empty());

        meetingService.getMeeting(404L);
    }

    @Test
    public void addMeeting() {
        Long writerId = 1004L;

        resource.setId(1L);

        given(userRepository.findById(eq(1004L)))
                .willReturn(
                        Optional.ofNullable(User.builder().id(1004L).build()));
        given(meetingRepository.save(any())).willReturn(resource);

        Meeting meeting = meetingService.addMeeting(resource, writerId);

        verify(meetingRepository).save(any());
        verify(userRepository).findById(eq(1004L));

        assertNotNull(meeting.getApplyingUsers());
        assertThat(meeting.getWriter().getId()).isEqualTo(1004L);
    }

    @Test(expected = UserNotFoundException.class)
    public void addMeetingWithInvalidWriter() {
        Long writerId = 404L;

        given(meetingRepository.findMeetingById(eq(404L)))
                .willReturn(Optional.empty());

        Meeting meeting = meetingService.addMeeting(resource, writerId);
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

        assertThat(modified.getTopicId()).isEqualTo(1L);
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

    @Test
    public void getApplyingUsers() {

        List<User> mockUsers = new ArrayList<>();

        mockUsers.add(User.builder().id(104L).build());

        Meeting mockMeeting = Mockito.mock(Meeting.class);

        given(meetingRepository.findMeetingById(eq(1004L)))
                .willReturn(Optional.ofNullable(mockMeeting));

        given(mockMeeting.getApplyingUsers())
                .willReturn(mockUsers);

        List<User> applyingUsers = meetingService.getApplyingUsers(1004L);

        assertThat(
                applyingUsers.get(0).getId()).isEqualTo(104L);

    }

    @Test(expected = MeetingNotFoundException.class)
    public void getApplyingUsersWithNotExistingMeetingId() {

        given(meetingRepository.findMeetingById(eq(404L)))
                .willReturn(Optional.empty());

        meetingService.getApplyingUsers(404L);

    }

    @Test
    public void addUserToList() {

        given(meetingRepository.findMeetingById(eq(1004L)))
                .willReturn(Optional.ofNullable(
                   Meeting.builder()
                           .id(1004L)
                           .applyingUsers(
                                   new ArrayList<User>())
                           .build()
                ));

        given(userRepository.findById(eq(104L)))
                .willReturn(Optional.ofNullable(
                        User.builder().id(104L).build()
                ));

        meetingService.addUserToList(1004L, 104L);

        verify(userRepository).findById(eq(104L));
        verify(meetingRepository).findMeetingById(eq(1004L));
        verify(meetingRepository).save(any());
    }
}