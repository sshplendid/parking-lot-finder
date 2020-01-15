package me.shawn.challenge.parkinglotapi.config;

import lombok.extern.slf4j.Slf4j;
import me.shawn.challenge.parkinglotapi.model.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Slf4j
@ControllerAdvice
@RestControllerAdvice
@RestController
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @GetMapping("/error")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Throwable.class})
    public CommonResponse error(Throwable t) {
        log.error("에러 발생: {}", t.getMessage());
        log.error(Arrays.stream(t.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(joining("\n")));

        return CommonResponse.builder()
                .status("ERROR")
                .message("에러가 발생했습니다.")
                .size(0)
                .data(new ArrayList<>())
                .build();
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public CommonResponse beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        CommonResponse.CommonResponseBuilder<Object> builder = CommonResponse.builder();
        String status = "OK";
        String message = "API 요청을 정상적으로 처리했습니다.";
        long size = 0;

        if(body instanceof CommonResponse) {
            return (CommonResponse) body;
        } else if(body instanceof List && ((List) body) != null) {
            builder.size(((List) body).size());
            builder.data((List<Object>) body);
        } else {
            // 응답 결과가 List가 아닌 경우, List에 넣어서 body에 다시 할당한다. 그리고 사이즈는 1로 설정한다.
            builder.size(1);
            List<Object> newList = new ArrayList<Object>();
            newList.add(body);
            builder.data(newList);
        }

        return builder
                .status(status)
                .message(message)
                .build();
    }
}
