package me.shawn.challenge.parkinglotapi.config;

import me.shawn.challenge.parkinglotapi.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@RestController
public class ResponseAdvice {

    @GetMapping("/error")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Throwable.class})
    public ErrorMessage error() {
        return new ErrorMessage("SERVER_ERROR", "서버 에러가 발생했습니다.");
    }

}
