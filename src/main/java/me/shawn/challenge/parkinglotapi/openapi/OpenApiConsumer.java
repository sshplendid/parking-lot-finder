package me.shawn.challenge.parkinglotapi.openapi;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.shawn.challenge.parkinglotapi.openapi.model.OpenApiResponse;
import me.shawn.challenge.parkinglotapi.openapi.model.OpenApiStatus;
import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.naming.SizeLimitExceededException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class OpenApiConsumer {
    @NonNull
    private RestTemplate restTemplate;
    private final ModelMapper modelMapper;
    private final ParkInfoParser parkInfoParser;


    private final String apiToken;
    private final String apiEndpoint;

    private static final String API_PATH = "/{token}/json/{apiServiceName}/{rowStartAt}/{rowEndAt}/{query1}/{query2}";
    private static final String EMPTY = " ";
    public static final String RESULT = "RESULT";
    static final String PARK_API_NAME = "GetParkInfo";

    public OpenApiConsumer(RestTemplate restTemplate, @Value("${common.open-api.token}") String apiToken, @Value("${common.open-api.endpoint}") String apiEndpoint) {
        this.modelMapper = new ModelMapper();
        this.parkInfoParser = new ParkInfoParser(modelMapper);
        this.restTemplate = restTemplate;
        this.apiToken = apiToken;
        this.apiEndpoint = apiEndpoint;
    }

    /**
     * 주차장 정보를 주소로 검색한다.
     * @param address 구 혹은 동과 같은 주소. (e.g. 마포)
     * @return 검색 조건에 맞는 결과
     * @throws SizeLimitExceededException 검색하고자 하는 조건이 1000 건을 초과하는 경우 발생한다.
     */
    public OpenApiResponse getParkInfoByAddress(int rowStartAt, int rowEndAt, String address) throws SizeLimitExceededException {
        return this.fetchApiData(OpenApiConsumer.PARK_API_NAME, rowStartAt, rowEndAt, address);
    }

    /**
     * 주차장 정보를 주차장 코드로 검색한다.
     * @param parkingCode 주차장 코드 (e.g. 1033754)
     * @return 검색 조건에 맞는 결과
     * @throws SizeLimitExceededException 검색하고자 하는 조건이 1000 건을 초과하는 경우 발생한다.
     */
    public OpenApiResponse getParkInfoByParkingCode(String parkingCode) throws SizeLimitExceededException {
        return this.fetchApiData(OpenApiConsumer.PARK_API_NAME, 1, 1, OpenApiConsumer.EMPTY, parkingCode);
    }

    OpenApiResponse fetchApiData(String apiName, int rowStartAt, int rowEndAt, String query1) throws SizeLimitExceededException {
        return this.fetchApiData(apiName, rowStartAt, rowEndAt, query1, EMPTY);

    }
    OpenApiResponse fetchApiData(String apiName, int rowStartAt, int rowEndAt, String query1, String query2) throws SizeLimitExceededException {
        validateRequest(rowStartAt, rowEndAt);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(apiEndpoint + API_PATH, HttpMethod.GET, null, Map.class, apiToken, apiName, rowStartAt, rowEndAt, query1, query2);

        if(responseEntity.getBody() != null && responseEntity.getBody().containsKey(RESULT) ) {
            String apiStatusCode = ((Map<String, String>) responseEntity.getBody().get(RESULT)).getOrDefault("CODE", "UNKNOWN");

            return OpenApiResponse.okButJust(apiStatusCode);
        }

        Map<String, Object> apiResult = (Map<String, Object>) responseEntity.getBody().get(apiName);
        OpenApiStatus openApiStatus = OpenApiStatus.resolve(((Map<String, String>) apiResult.get(RESULT)).getOrDefault("CODE", "UNKNOWN"));

        if (openApiStatus != OpenApiStatus.OK) {
            return OpenApiResponse.okButJust(openApiStatus);
        }

        List<ParkInfoDTO> data = ((List<Map<String, Object>>) ((Map<String, Object>) responseEntity.getBody().get(PARK_API_NAME)).get("row")).stream()
                .map(map -> parkInfoParser.parse(map))
                .collect(Collectors.toList());

        return OpenApiResponse.builder()
                .status(responseEntity.getStatusCode())
                .size((Integer) apiResult.get("list_total_count"))
                .apiStatus(openApiStatus)
                .data(data)
                .build();
    }

    private void validateRequest(int rowStartAt, int rowEndAt) throws SizeLimitExceededException {
        if ((rowEndAt - rowStartAt + 1) > 1000) {
            throw new SizeLimitExceededException("SHN-429: API 요청 데이터의 크기는 1000 이하여야 합니다. size: " + (rowEndAt - rowStartAt + 1));
        }
        if (rowStartAt > rowEndAt) {
            throw new IllegalArgumentException("SHN-124: API 요청 시작위치는 요청 종료위치보다 작거나 같아야 합니다. rowStartAt: " + rowStartAt + ", rowEndAt: " + rowEndAt);
        }
    }
}
