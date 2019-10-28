package kr.gringrape.hamp.application.exceptions;

public class MeetingNotFoundException extends RuntimeException {

    public MeetingNotFoundException(Long id) {
        super("request meeting id: " + id + "is not found");
    }

}
