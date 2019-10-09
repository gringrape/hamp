package kr.gringrape.hamp.interfaces;

public class PasswordWrongException extends RuntimeException {

    public PasswordWrongException(String email) {
        super("password doesn't match");
    }

}
