package kr.gringrape.hamp.interfaces.exceptionHandlers;

import kr.gringrape.hamp.application.exceptions.EmailNotExistedException;
import kr.gringrape.hamp.application.exceptions.PasswordWrongException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class SessionControllerAdvisor {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordWrongException.class)
    public @ResponseBody String passwordWrongExceptionHandler(
            PasswordWrongException exception
    ) {
        log.error(exception.getMessage());

        return "{}";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailNotExistedException.class)
    public @ResponseBody String emailNotExistedExceptionHandler(
            EmailNotExistedException ex
    ) {
        log.error(ex.getMessage());

        return "{}";
    }
}
