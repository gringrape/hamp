package kr.gringrape.hamp.interfaces;

import kr.gringrape.hamp.application.MeetingService;
import kr.gringrape.hamp.domain.Meeting;
import kr.gringrape.hamp.application.exceptions.MeetingNotFoundException;
import kr.gringrape.hamp.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                .build();

        meetings.add(meeting);
    }

    @Test
    public void list() throws Exception {

        Page mockPage = mock(Page.class);

        given(meetingService.getPage(
                any(), any(), any(), any(), any(), any()
        )).willReturn(mockPage);

        given(mockPage.getTotalPages()).willReturn(1);

        mvc.perform(get("/meetings"))
                .andExpect(status().isOk())
                .andExpect(header().string("totalPages","1"));

        verify(meetingService).getPage(
                any(), any(), any(), any(), any(), any()
        );
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
                    .writer(User.builder().id(1004L).build())
                    .build();

        given(meetingService.addMeeting(any(), any()))
                .willReturn(meeting);

        mvc.perform(post("/meetings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"topicId\":1,\"title\":\"함께 C언어 공부해요~!\",\"description\":\"초보자도 쉽게 할 수 있어요~!\",\"logitude\":\"100\",\"latitude\":\"100\",\"address\":\"서울시 광진구\"}")
                .header("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIzMCwidXNlck5hbWUiOiJraW0ifQ.2uObcw6FRnexVMUmf6xC_aUTn2u3bXwfiJ5USQmpBds"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/meetings/1004"));

        ArgumentCaptor<Meeting> captor = ArgumentCaptor.forClass(Meeting.class);

        verify(meetingService).addMeeting(captor.capture(), any());

        Meeting mockResource = captor.getValue();

        assertThat(meeting.getWriter().getId()).isEqualTo(1004L);
        assertThat(mockResource.getLatitude()).isEqualTo(100);
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