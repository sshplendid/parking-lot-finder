package me.shawn.challenge.parkinglotapi.config;

import lombok.extern.slf4j.Slf4j;
import me.shawn.challenge.parkinglotapi.model.CommonResponse;
import me.shawn.challenge.parkinglotapi.model.ErrorMessage;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@Slf4j
@ControllerAdvice
@RestControllerAdvice
@RestController
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @GetMapping("/error")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Throwable.class})
    public ErrorMessage error(Throwable t) {
        log.error("에러 발생: {}", t.getMessage());
        return new ErrorMessage("SERVER_ERROR", "서버 에러가 발생했습니다.");
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public CommonResponse beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String status = "OK";
        String message = "API 요청을 정상적으로 처리했습니다.";
        long size = -1;

        if(body instanceof List && ((List) body) != null) {
            size = ((List) body).size();
        }

        return CommonResponse.builder()
                .status(status)
                .message(message)
                .size(size)
                .data((List<Object>) body)
                .build();
    }
}
