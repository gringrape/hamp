package kr.gringrape.hamp.interfaces;

import kr.gringrape.hamp.application.UserService;
import kr.gringrape.hamp.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserService userService;

    @Test
    public void create() throws Exception {

        String email = "tester@gmail.com";
        String nick = "tester";
        String password = "test";

        User user = User.builder()
                .id(1004L)
                .email(email)
                .nick(nick)
                .password(password)
                .build();

        given(userService.registerUser(eq(email), eq(nick), eq(password)))
                .willReturn(user);

        mvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"tester@gmail.com\",\"nick\":\"tester\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/users/1004"));

        verify(userService).registerUser(email, nick, password);

    }

}