package kr.gringrape.hamp.application;

public class EmailNotExistedException extends RuntimeException {

    public EmailNotExistedException() {
        super("email is not existed~!");
    }

}
