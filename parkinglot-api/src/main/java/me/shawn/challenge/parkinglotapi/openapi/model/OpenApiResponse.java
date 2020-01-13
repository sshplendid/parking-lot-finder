package me.shawn.challenge.parkinglotapi.openapi.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder @Getter
public class OpenApiResponse {
    private int size;
    private List<ParkInfoDTO> data;
    private HttpStatus status;
    private OpenApiStatus apiStatus;

    /**
     * {@link HttpStatus} 는 200 OK 이지만, API 결과 코드가 정상이 아니거나 데이터가 없을 때의 응답결과를 리턴한다.
     * @param openApiStatus 응답 결과의 OpenApi 결과상태
     * @return data가 비어있는 응답결과
     * @see OpenApiStatus
     * @see HttpStatus
     */
    public static OpenApiResponse okButJust(OpenApiStatus openApiStatus) {
        return OpenApiResponse.builder()
                .status(HttpStatus.OK)
                .apiStatus(openApiStatus)
                .data(Collections.emptyList())
                .build();
    }

    /**
     * {@link HttpStatus} 는 200 OK 이지만, API 결과 코드가 정상이 아니거나 데이터가 없을 때의 응답결과를 리턴한다.
     * @param openApiStatusCode 응답 결과의 OpenApi 결과상태 코드(RESULT.CODE)
     * @return data가 비어있는 응답결과
     * @see OpenApiStatus
     * @see HttpStatus
     */
    public static OpenApiResponse okButJust(String openApiStatusCode) {
        return OpenApiResponse.okButJust(OpenApiStatus.resolve(openApiStatusCode));
    }
}
