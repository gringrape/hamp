package kr.gringrape.hamp.domain;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UserTests {

    @Test
    public void creation() {

        User user = User.builder()
                .email("wlsdl8012@naver.com")
                .nick("Jin")
                .level(2)
                .build();

        assertThat(user.getNick(), is("Jin"));

        assertThat(user.isAdmin(), is(true));
    }

    @Test
    public void deactivate() {

        User user = User.builder()
                .email("wlsdl8012@naver.com")
                .nick("Jin")
                .level(2)
                .build();

        user.deactivate();

        assertThat(user.getLevel(), is(0));

    }

    @Test
    public void isActive() {

        User user = User.builder()
                .email("wlsdl8012@naver.com")
                .nick("Jin")
                .level(0)
                .build();

        assertThat(user.isActive(), is(false));
    }
}