package kr.gringrape.hamp.interfaces;

import io.jsonwebtoken.Claims;
import kr.gringrape.hamp.HampApplication;
import kr.gringrape.hamp.application.MeetingService;
import kr.gringrape.hamp.domain.MeetingNotFoundException;
import kr.gringrape.hamp.domain.User;
import kr.gringrape.hamp.filters.CustomUser;
import kr.gringrape.hamp.utils.JwtUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ApplyingUsersController.class)
public class ApplyingUsersControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    MeetingService meetingService;

    @Test
    public void listWithValidData() throws Exception {

        List<User> mockUsers = new ArrayList<>();

        mockUsers.add(
                User.builder()
                        .id(200L)
                        .email("wlsdl8012@naver.com")
                        .nick("Jin")
                        .level(1)
                        .build());

        given(meetingService.getApplyingUsers(eq(1004L)))
                .willReturn(mockUsers);

        mvc.perform(get("/meetings/1004/applyingUsers"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":200")));

        verify(meetingService).getApplyingUsers(eq(1004L));
    }

    @Test
    public void listWithNotExistingMeetingId() throws Exception {

        given(meetingService.getApplyingUsers(404L))
                .willThrow(MeetingNotFoundException.class);

        mvc.perform(get("/meetings/404/applyingUsers"))
                .andExpect(status().isNotFound());

        verify(meetingService).getApplyingUsers(eq(404L));
    }

    @Test
    public void addUser() throws Exception {

        mvc.perform(post("/meetings/1004/applyingUsers")
                .header("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEzMSwidXNlck5hbWUiOiJKaW4ifQ.cs7BHDpUOJ6ex1u4wp68trGrLEg8ovT5N6PrIDN1G9s"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/meetings/1004/applyingUsers/131"));

        verify(meetingService).addUserToList(eq(1004L), eq(131L));

    }
}