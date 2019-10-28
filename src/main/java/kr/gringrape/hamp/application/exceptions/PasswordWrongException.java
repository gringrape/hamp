package kr.gringrape.hamp.application.exceptions;

public class PasswordWrongException extends RuntimeException {

    public PasswordWrongException(String email) {
        super("password doesn't match");
    }

}
