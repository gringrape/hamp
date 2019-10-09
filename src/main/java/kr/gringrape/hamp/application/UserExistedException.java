package kr.gringrape.hamp.application;

public class UserExistedException extends RuntimeException {

    public UserExistedException(String email) {

        super(email + " is existed");

    }

}
