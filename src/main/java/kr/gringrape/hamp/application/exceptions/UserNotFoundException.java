package kr.gringrape.hamp.application.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super("cannot find user: " + userId);
    }

}
