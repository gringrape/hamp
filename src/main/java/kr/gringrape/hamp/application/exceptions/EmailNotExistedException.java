package kr.gringrape.hamp.application.exceptions;

public class EmailNotExistedException extends RuntimeException {

    public EmailNotExistedException() {
        super("email is not existed~!");
    }

}
