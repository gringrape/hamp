package kr.gringrape.hamp.interfaces.exceptionHandlers;

import kr.gringrape.hamp.application.exceptions.MeetingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MeetingControllerAdvisor {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MeetingNotFoundException.class)
    public @ResponseBody String handler_MeetingNotFound() {

        return "{}";

    }

}
