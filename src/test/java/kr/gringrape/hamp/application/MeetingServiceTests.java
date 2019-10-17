package kr.gringrape.hamp.application;

import kr.gringrape.hamp.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
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
    
    @Before
    public void setMockMeetingsForFilter() {

        mockMeetingsForFilter = new ArrayList<>();

        mockMeetingsForFilter.add(
                Meeting.builder()
                        .address("서울")
                        .topicId(2L)
                        .title("아주 재미있는 모임입니다")
                        .startDate(LocalDateTime.of(2019, 10, 15, 13, 0))
                        .endDate(LocalDateTime.of(2019, 10, 15, 15, 0))
                        .build());

        mockMeetingsForFilter.add(
                Meeting.builder()
                        .address("부산")
                        .topicId(1L)
                        .title("아주 재미없는 모임입니다")
                        .startDate(LocalDateTime.of(2019, 9, 15, 13, 0))
                        .endDate(LocalDateTime.of(2019, 9, 15, 15, 0))
                        .build());

        mockMeetingsForFilter.add(
                Meeting.builder()
                        .address("서울")
                        .topicId(1L)
                        .title("아주 재미있는 모임입니다")
                        .startDate(LocalDateTime.of(2019, 8, 15, 13, 0))
                        .endDate(LocalDateTime.of(2019, 8, 15, 15, 0))
                        .build());
        
    }

    @Test
    public void getMeetingsByTopic() {

        given(meetingRepository.findAll())
                .willReturn(mockMeetingsForFilter);

        List<Meeting> meetings =
                meetingService.getMeetings(
                        Optional.of("서울"),
                        Optional.of(1L),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                );

        assertThat(meetings.size()).isEqualTo(1);
    }

    @Test
    public void getMeetingsByAddress() {

        given(meetingRepository.findAll())
                .willReturn(mockMeetingsForFilter);

        List<Meeting> meetings =
                meetingService.getMeetings(
                        Optional.of("부산"),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                );

        assertThat(meetings.size()).isEqualTo(1);
    }

    @Test
    public void getMeetingsByTitle() {

        given(meetingRepository.findAll())
                .willReturn(mockMeetingsForFilter);

        List<Meeting> meetings =
                meetingService.getMeetings(
                        Optional.of("부산"),
                        Optional.empty(),
                        Optional.of("없는"),
                        Optional.empty(),
                        Optional.empty()
                );

        assertThat(meetings.size()).isEqualTo(1);

    }

    @Test
    public void getMeetingsByDuration() {

        given(meetingRepository.findAll())
                .willReturn(mockMeetingsForFilter);

        List<Meeting> meetings =
                meetingService.getMeetings(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(LocalDateTime.of(2019, 9, 1, 0, 0)),
                        Optional.of(LocalDateTime.of(2019, 11, 1, 23, 59))
                );

        assertThat(meetings.size()).isEqualTo(2);

    }

    @Test
    public void getMeetingWithExistingId() {
        given(meetingRepository.findMeetingById(1L))
                .willReturn(Optional.ofNullable(meeting));

       Meeting theMeeting = meetingService.getMeeting(1L);

       verify(meetingRepository).findMeetingById(eq(1L));

       assertThat(theMeeting.getTopicId()).isEqualTo(2L);
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

        assertNotNull(meeting.getApplyingUsers());
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

        Meeting meeting = Meeting.builder().id(1004L).build();

        given(meetingRepository.findMeetingById(eq(1004L)))
                .willReturn(
                        Optional.ofNullable(meeting));

        given(userRepository.findAllByAppliedMeetings(eq(meeting)))
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