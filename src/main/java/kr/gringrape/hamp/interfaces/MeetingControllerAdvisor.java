package kr.gringrape.hamp.interfaces;

import kr.gringrape.hamp.domain.MeetingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
