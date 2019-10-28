package kr.gringrape.hamp.application;

import kr.gringrape.hamp.application.exceptions.EmailNotExistedException;
import kr.gringrape.hamp.application.exceptions.UserExistedException;
import kr.gringrape.hamp.application.exceptions.UserNotFoundException;
import kr.gringrape.hamp.domain.User;
import kr.gringrape.hamp.infrastructure.persistence.UserRepository;
import kr.gringrape.hamp.application.exceptions.PasswordWrongException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class UserServiceTests {

    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        this.userService = new UserService(userRepository, passwordEncoder);

    }

    @Test
    public void getUsers() {

        List<User> mockUsers = new ArrayList<>();

        User mockUser = User.builder()
                .email("test@gmail.com")
                .nick("Jin")
                .level(2)
                .build();

        mockUsers.add(mockUser);

        given(userRepository.findAll())
                .willReturn(mockUsers);

        List<User> users = userService.getUsers();

        verify(userRepository).findAll();

        User user = users.get(0);

        assertThat(user.getNick())
                .isEqualTo("Jin");

    }

    @Test
    public void getUserWithValidId() {

        User mockUser = User.builder().id(1004L).build();

        given(userRepository.findById(1004L))
                .willReturn(Optional.ofNullable(mockUser));

        User user = userService.getUser(1004L);

        verify(userRepository).findById(eq(1004L));

        assertThat(user.getId()).isEqualTo(1004L);

    }

    @Test(expected = UserNotFoundException.class)
    public void getUserWithInvalidId() {

        given(userRepository.findById(404L))
                .willReturn(Optional.empty());

        User user = userService.getUser(404L);

    }

    @Test
    public void updateUser() {

        Long id = 1L;
        Integer level = 1;
        String email = "tester@gmail.com";
        String nick = "tester";
        String password = "1234";

        User mockUser = User.builder()
                .email(email).nick(nick).id(id).level(level).build();

        User updated = User.builder()
                .email(email).nick("Superman").id(id).level(level).build();

        given(userRepository.findById(eq(1L)))
                .willReturn(java.util.Optional.ofNullable(mockUser));

        given(userRepository.save(any()))
                .willReturn(updated);

        User user = userService.updateUser(id, email, password, nick);

        verify(userRepository).findById(eq(id));

        verify(userRepository).save(any());

        assertThat(user.getNick())
                .isEqualTo("Superman");
    }

    @Test
    public void deactivateUser() {

        User mockUser = User.builder().build();

        User deactivated = User.builder().level(0).build();

        given(userRepository.findById(eq(1L)))
                .willReturn(java.util.Optional.ofNullable(mockUser));

        given(userRepository.save(any()))
                .willReturn(deactivated);

        User user = userService.deactivateUser(1L);

        verify(userRepository).findById(eq(1L));

        assertThat(user.getLevel()).isEqualTo(0);
    }

    @Test
    public void registerUser() {

        String email = "tester@gmail.com";
        String nick = "tester";
        String password = "test";

        userService.registerUser(email, nick, password);

        verify(userRepository).save(any());
    }

    @Test(expected = UserExistedException.class)
    public void registerWithEmailExistedUser() {

        String email = "tester@gmail.com";
        String nick = "tester";
        String password = "test";

        User user = User.builder().build();

        given(userRepository.findByEmail(eq(email)))
                .willReturn(Optional.of(user));

        userService.registerUser(email, nick, password);

        verify(userRepository, never()).save(any());

    }

    @Test
    public void authenticateWithExistedEmail() {

        String email = "tester@test.com";
        String password = "test";

        User mockUser = User.builder()
                .email(email)
                .build();

        given(userRepository.findByEmail(any()))
                .willReturn(Optional.ofNullable(mockUser));

        given(passwordEncoder.matches(any(), any()))
                .willReturn(true);

        User user = userService.authenticate(email, password);

        verify(userRepository).findByEmail(eq(email));

        assertThat(user.getEmail()).isEqualTo(email);

    }

    @Test(expected = EmailNotExistedException.class)
    public void authenticateWithNotExistedEmail() {

        String email = "x@test.com";
        String password = "test";

        given(userRepository.findByEmail(eq(email)))
                .willReturn(empty());

        userService.authenticate(email, password);

        verify(userRepository).findByEmail(eq(email));

    }

    @Test(expected = PasswordWrongException.class)
    public void authenticateWithWrongPassword() {

        String email = "tester@test.com";
        String password = "x";

        User mockUser = User.builder().password("y").build();

        given(userRepository.findByEmail(eq(email)))
                .willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(), any()))
                .willReturn(false);

        userService.authenticate(email, password);

        verify(userRepository).findByEmail(eq(email));

    }
}