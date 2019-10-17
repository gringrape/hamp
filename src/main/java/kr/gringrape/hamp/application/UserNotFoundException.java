package kr.gringrape.hamp.application;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super("cannot find user: " + userId);
    }

}
