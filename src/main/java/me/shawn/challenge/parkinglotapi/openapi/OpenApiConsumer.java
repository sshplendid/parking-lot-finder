package me.shawn.challenge.parkinglotapi.openapi;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.naming.SizeLimitExceededException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class OpenApiConsumer<T>   {
    @NonNull
    private RestTemplate restTemplate;
    private static final String API_URL = "http://openapi.seoul.go.kr:8088/{apiToken}/json/{apiName}/{rowStartAt}/{rowEndAt}/{address}/";
    private final String API_TOKEN = "6a756259796a756d32304559665677";

    public OpenApiResponse<T> fetchApiData(String apiName, int rowStartAt, int rowEndAt, String address) throws SizeLimitExceededException {
        if((rowEndAt - rowStartAt + 1) > 1000) {
            throw new SizeLimitExceededException("ERR-429: API 요청 데이터의 크기는 1000 이하여야 합니다.");
        }
        ResponseEntity<Map> responseEntity = restTemplate.exchange(API_URL, HttpMethod.GET, null, Map.class, API_TOKEN, apiName, rowStartAt, rowEndAt, address);
        Map<String, Object> apiResult = (Map<String, Object>) responseEntity.getBody().get(apiName);
        OpenApiStatus openApiStatus = OpenApiStatus.resolve(((Map<String, String>) apiResult.get("RESULT")).getOrDefault("CODE", "UNKNOWN"));

        switch(openApiStatus) {
            case SERVER_ERROR:
            case NO_DATA:
            case TOO_MANY_REQUESTS:
                return OpenApiResponse.OkButJust(openApiStatus);
        }

        OpenApiResponse<T> apiResponse = OpenApiResponse.<T>builder()
                .status(responseEntity.getStatusCode())
                .size((Integer) apiResult.get("list_total_count"))
                .apiStatus(openApiStatus)
                .data((List<T>) apiResult.get("row"))
                .build();
        return apiResponse;
    }


}
