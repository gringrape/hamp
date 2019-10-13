package kr.gringrape.hamp.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MeetingTests {

    @Test
    public void addUserNotExisted() {
        Meeting meeting = Meeting.builder().build();

        List<User> mockUserList = new ArrayList<>();
        mockUserList.add(User.builder().id(1004L).build());

        meeting.setApplyingUsers(mockUserList);

        User user = User.builder().id(1005L).build();

        boolean result = meeting.addUser(user);

        assertThat(result).isTrue();
        assertThat(meeting.getApplyingUsers().size())
                .isEqualTo(2);
    }

    @Test
    public void addUserExisted() {
        Meeting meeting = Meeting.builder().build();

        List<User> mockUserList = new ArrayList<>();
        mockUserList.add(User.builder().id(1004L).build());

        meeting.setApplyingUsers(mockUserList);

        User user = User.builder().id(1004L).build();

        boolean result = meeting.addUser(user);

        assertThat(result).isFalse();

        assertThat(meeting.getApplyingUsers().size())
                .isEqualTo(1);
    }
}