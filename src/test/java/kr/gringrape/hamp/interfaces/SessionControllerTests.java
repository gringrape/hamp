package kr.gringrape.hamp.interfaces;

import kr.gringrape.hamp.application.EmailNotExistedException;
import kr.gringrape.hamp.application.UserService;
import kr.gringrape.hamp.domain.User;
import kr.gringrape.hamp.utils.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoPostProcessor;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserService userService;

    @Test
    public void userInfo() throws Exception {

        mvc.perform(get("/session")
                .header("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIzMCwidXNlck5hbWUiOiJraW0ifQ.2uObcw6FRnexVMUmf6xC_aUTn2u3bXwfiJ5USQmpBds"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/users/230"));

    }

    @Test
    public void createWithValidRequest() throws Exception {

        String email = "tester@gmail.com";
        String password = "test";

        User user = User.builder()
                .email(email)
                .id(1004L)
                .nick("John")
                .build();

        given(userService.authenticate(any(), any()))
                .willReturn(user);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@gmail.com\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/session"))
                .andExpect(header().string("accessToken", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsInVzZXJOYW1lIjoiSm9obiJ9.X_V7bklVELDz50tbp5w5Qj01igVrwHn9ENiogAB_rOw"));

        verify(userService)
                .authenticate(eq("tester@gmail.com"), eq("test"));
    }

    @Test
    public void createWithInvalidPassword() throws Exception {

        String email = "tester@gmail.com";
        String password = "x";

        given(userService.authenticate(email, password))
                .willThrow(new PasswordWrongException(email));

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@gmail.com\",\"password\":\"x\"}"))
                .andExpect(status().isBadRequest());

        verify(userService)
                .authenticate(eq("tester@gmail.com"), eq("x"));
    }

    @Test
    public void createWithEmailNotExisted() throws Exception {

        String email = "x";
        String password = "test";

        given(userService.authenticate(email, password))
                .willThrow(new EmailNotExistedException());

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"x\",\"password\":\"test\"}"))
                .andExpect(status().isBadRequest());

        verify(userService)
                .authenticate(eq("x"), eq("test"));
    }
}