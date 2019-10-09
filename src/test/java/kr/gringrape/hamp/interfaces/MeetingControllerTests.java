package kr.gringrape.hamp.interfaces;

import kr.gringrape.hamp.application.MeetingService;
import kr.gringrape.hamp.domain.Meeting;
import kr.gringrape.hamp.domain.MeetingNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MeetingController.class)
public class MeetingControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    MeetingService meetingService;

    /* fixture */
    List<Meeting> meetings;
    Meeting meeting;

    @Before
    public void setUp() {
        meetings = new ArrayList<>();

        meeting = Meeting.builder()
                .id(1L)
                .topicId(1L)
                .title("함께 C언어 공부해요~!")
                .description("초보자도 쉽게 할 수 있어요~!")
                .meetDate("2001-12-23 11:23:38")
                .noAppliers(1)
                .build();

        meetings.add(meeting);
    }

    @Test
    public void list() throws Exception {
        given(meetingService.getMeetings("", null))
                .willReturn(meetings);

        mvc.perform(get("/meetings"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"topicId\":1")));

        verify(meetingService).getMeetings("", null);
    }

    @Test
    public void listFilteredByRegion() throws Exception {
        List<Meeting> meetings = new ArrayList<>();

        meetings.add(Meeting.builder()
                    .title("서울 개발 모임")
                    .build());

        given(meetingService.getMeetings(eq("서울"), eq(null)))
                .willReturn(meetings);

        mvc.perform(get("/meetings?address=서울"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"title\":\"서울 개발 모임\"")));

        verify(meetingService).getMeetings(eq("서울"), eq(null));
    }

    @Test
    public void listFilteredByTopic() throws Exception {

        List<Meeting> meetings = new ArrayList<>();

        meetings.add(Meeting.builder()
                .title("서울 개발 모임")
                .build());

        given(meetingService.getMeetings("", 1L))
                .willReturn(meetings);

        mvc.perform(get("/meetings?topic=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"title\":\"서울 개발 모임\"")));

        verify(meetingService).getMeetings(eq(""), eq(1L));

    }

    @Test
    public void listFilteredByRegionAndTopic() throws Exception {

        List<Meeting> meetings = new ArrayList<>();

        meetings.add(Meeting.builder()
                .title("서울 개발 모임")
                .build());

        given(meetingService.getMeetings(eq("서울"), eq(1L)))
                .willReturn(meetings);

        mvc.perform(get("/meetings?address=서울&topic=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"title\":\"서울 개발 모임\"")));

        verify(meetingService).getMeetings(eq("서울"),eq(1L));

    }

    @Test
    public void detailWithExistingId() throws Exception {
        given(meetingService.getMeeting(1L))
                .willReturn(meeting);

        mvc.perform(get("/meetings/1"))
               .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"topicId\":1")));

        verify(meetingService).getMeeting(eq(1L));
    }

    @Test
    public void detailNotFound() throws Exception {
        given(meetingService.getMeeting(404L))
                .willThrow(new MeetingNotFoundException(404L));

        mvc.perform(get("/meetings/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }

    @Test
    public void createWithValidInfo() throws Exception {
        Meeting meeting = Meeting.builder()
                    .id(1004L)
                    .topicId(1L)
                    .title("함께 C언어 공부해요~!")
                    .description("초보자도 쉽게 할 수 있어요~!")
                    .build();

        given(meetingService.addMeeting(any()))
                .willReturn(meeting);

        mvc.perform(post("/meetings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"topicId\":1,\"title\":\"함께 C언어 공부해요~!\",\"description\":\"초보자도 쉽게 할 수 있어요~!\",\"address\":\"서울시 광진구 구의동 자바개발소\"}"))
                .andExpect(status().isCreated());

        verify(meetingService).addMeeting(any());
    }

    @Test
    public void createWithInvalidInfo() throws Exception {
        mvc.perform(post("/meetings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"topicId\":\"\",\"title\":\"함께 C언어 공부해요~!\",\"description\":\"초보자도 쉽게 할 수 있어요~!\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void modifyWithExistingId() throws Exception {
        mvc.perform(patch("/meetings/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"topicId\":1, \"title\":\"C 언어를 함께 공부합시다~\", \"description\":\"C 언어를 함께 공부하는 모임이에요~ 오셔서 구경해보세요~\",\"address\":\"서울시 광진구 구의동 자바개발소\"}"))
                .andExpect(status().isOk());

        verify(meetingService).modifyMeeting(eq(1L), any());

    }

    @Test
    public void modifyWithNotExistingId() throws Exception {
        given(meetingService.modifyMeeting(eq(404L), any()))
                .willThrow(new MeetingNotFoundException(404L));

        mvc.perform(patch("/meetings/404")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"topicId\":1, \"title\":\"C 언어를 함께 공부합시다~\", \"description\":\"C 언어를 함께 공부하는 모임이에요~ 오셔서 구경해보세요~\",\"address\":\"서울시 광진구 구의동 자바개발소\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void modifyWithInvalidInfo() throws Exception {
        mvc.perform(patch("/meetings/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"topicId\":\"\", \"title\":\"C 언어를 함께 공부합시다~\", \"description\":\"C 언어를 함께 공부하는 모임이에요~ 오셔서 구경해보세요~\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void removeWithExistingId() throws Exception {

        mvc.perform(delete("/meetings/1"))
                .andExpect(status().isNoContent());

        verify(meetingService).removeMeeting(eq(1L));

    }

    @Test
    public void removeWithNotExistingId() throws Exception {
        doThrow(new MeetingNotFoundException(404L))
                .when(meetingService).removeMeeting(eq(404L));

        mvc.perform(delete("/meetings/404"))
                .andExpect(status().isNotFound());
    }
}