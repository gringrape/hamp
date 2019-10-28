package kr.gringrape.hamp.application;

import kr.gringrape.hamp.application.exceptions.EmailNotExistedException;
import kr.gringrape.hamp.application.exceptions.UserExistedException;
import kr.gringrape.hamp.application.exceptions.UserNotFoundException;
import kr.gringrape.hamp.domain.User;
import kr.gringrape.hamp.infrastructure.persistence.UserRepository;
import kr.gringrape.hamp.application.exceptions.PasswordWrongException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public User updateUser(Long id, String email, String password, String nick) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        String encoded = passwordEncoder.encode(password);

        user.setEmail(email);
        user.setNick(nick);
        user.setPassword(encoded);

        User updated = userRepository.save(user);

        return updated;
    }

    public User deactivateUser(Long userId) {

        // TODO: 검색한 결과가 없을 경우에 예외 처리.
        User target = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        target.deactivate();

        User user = userRepository.save(target);

        return user;

    }

    public User registerUser(String email, String nick, String password) {

        Optional<User> existed = userRepository.findByEmail(email);

        if (existed.isPresent()) {
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

        if (!passwordEncoder
                .matches(password, user.getPassword())) {
            throw new PasswordWrongException("password doesn't match");
        }

        return user;

    }

    public User getUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return user;

    }
}
