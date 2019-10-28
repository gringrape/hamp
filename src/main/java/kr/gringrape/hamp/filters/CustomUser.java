package kr.gringrape.hamp.filters;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CustomUser extends User {

    private Long userId;

    private String userName;

    public CustomUser(Long userId, String userName, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        setUserId(userId);
        setUserName(userName);
    }
}
