package kr.gringrape.hamp.application.exceptions;

public class UserExistedException extends RuntimeException {

    public UserExistedException(String email) {

        super(email + " is existed");

    }

}
