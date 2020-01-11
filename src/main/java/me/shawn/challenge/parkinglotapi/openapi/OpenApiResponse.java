package me.shawn.challenge.parkinglotapi.openapi;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder @Getter
class OpenApiResponse<T> {
    private int size;
    private List<T> data;
    private HttpStatus status;
    private OpenApiStatus apiStatus;
    private String resultCode;
    private String resultMessage;

    public static OpenApiResponse OkButJust(OpenApiStatus openApiStatus) {
        return OpenApiResponse.builder()
                .status(HttpStatus.OK)
                .apiStatus(openApiStatus)
                .build();
    }
}
