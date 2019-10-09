package kr.gringrape.hamp.application;

import kr.gringrape.hamp.domain.User;
import kr.gringrape.hamp.domain.UserRepository;
import kr.gringrape.hamp.interfaces.PasswordWrongException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers() {

        List<User> users = userRepository.findAll();

        return users;

    }

    public User addUser(String email, String nick) {

        User resource = User.builder().email(email).nick(nick).build();

        resource.setLevel(1);

        User user = userRepository.save(resource);

        return user;

    }

    public User updateUser(Long id, String email, String nick, Integer level) {

        User user = userRepository.findById(id).orElse(null);

        user.setEmail(email);
        user.setNick(nick);
        user.setLevel(level);

        User updated = userRepository.save(user);

        return updated;
    }

    public User deactivateUser(Long userId) {

        // TODO: 검색한 결과가 없을 경우에 예외 처리.
        User target = userRepository.findById(userId).orElse(null);

        target.deactivate();

        User user = userRepository.save(target);

        return user;

    }

    public User registerUser(String email, String nick, String password) {

        Optional<User> existed = userRepository.findByEmail(email);

        if(existed.isPresent()) {
            throw new UserExistedException(email);
        }

        String encodedPassword = passwordEncoder.encode(password);

        User resource = User.builder()
                .email(email)
                .nick(nick)
                .level(1)
                .password(encodedPassword)
                .build();

        User user = userRepository.save(resource);

        return user;
    }

    public User authenticate(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(EmailNotExistedException::new);

        if(!passwordEncoder
                .matches(password, user.getPassword())) {
            throw new PasswordWrongException("password doesn't match");
        }

        return user;

    }
}
