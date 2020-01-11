package me.shawn.challenge.parkinglotapi.openapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import javax.naming.SizeLimitExceededException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class OpenApiConsumerTest {

    private OpenApiConsumer<ParkingInfo> openApiConsumer;
    private RestTemplate restTemplate;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        openApiConsumer = new OpenApiConsumer<>(restTemplate);
        modelMapper = new ModelMapper();
    }

    @Test
    void apiFetchTest() throws SizeLimitExceededException {
        // GIVEN
        int rowStartAt = 1;
        int rowEndAt = 5;
        String address = "마포";

        // WHEN
        OpenApiResponse<ParkingInfo> response = openApiConsumer.fetchApiData(ParkingInfo.API_NAME, rowStartAt, rowEndAt, address);

        // THEN
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getApiStatus()).isEqualTo(OpenApiStatus.OK);
        assertThat(response.getData()).isNotEmpty();
        assertThat(response.getData()).hasSizeLessThanOrEqualTo((rowEndAt - rowStartAt + 1));
    }

    @Test
    void maxSizeTest() throws SizeLimitExceededException {
        // GIVEN

        // WHEN
        int size = 1000;
        int i = 0;
        int total = 0;
        while(size == 1000) {
            int rowStartAt = i * 1000 + 1;
            int rowEndAt = i * 1000 + 1000;

            OpenApiResponse<ParkingInfo> response =
                    openApiConsumer.fetchApiData(ParkingInfo.API_NAME, rowStartAt, rowEndAt, "");
            total += response.getData().size();

            log.info("try seq {}: real size -> {}", i+1, response.getData().size());
            log.info("누적 데이터: {}", total);

            ++i;
        }

        // THEN
    }

    @Test
    void sizeLimitExceededExceptionInClientSideTest() {
        // GIVEN
        int rowStartAt = 1;
        int rowEndAt = 1001;
        String address = "서울";

        // WHEN and THEN
        assertThrows(SizeLimitExceededException.class, () -> {
            OpenApiResponse<ParkingInfo> response = openApiConsumer.fetchApiData(ParkingInfo.API_NAME, rowStartAt, rowEndAt, address);
        });
    }



    /*
    @Test
    void 테스트_오픈API테스트1() {
        // GIVEN
        int pageStartAt = 1;
        int pageEndAt = 5;
        String address = "마포";

        // WHEN
        Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);
        OpenApiResponse<ParkingInfo> result = modelMapper.map(response.get("GetParkInfo"), (OpenApiResponse.class));
        log.info("result: {}", result);

        // THEN
        assertThat(response).isNotNull();
        assertThat(result.getData()).isNotEmpty();
        assertThat(result.getData().size()).isEqualTo(result.getSize());
        assertThat(result.getData()).hasSize(5);
    }

    @Test
    void 테스트_오픈API테스트2() {
        // GIVEN
        int rowStartAt = 1000;
        int rowEndAt = 2000;
        String address = "망원";

        // WHEN
        ResponseEntity<Map> response = restTemplate.exchange(API_URL, HttpMethod.GET, null, Map.class, API_TOKEN, rowStartAt, rowEndAt, address);
        OpenApiResponse<ParkingInfo> result = modelMapper.map(response.getBody().get("GetParkInfo"), (OpenApiResponse.class));

        log.info("row size: {}", result.getSize());
        log.info("row result: {}", result.getData());

        // THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getData()).isNotEmpty();
        assertThat(result.getData()).hasSizeLessThanOrEqualTo((rowEndAt - rowStartAt + 1));
    }
     */
}
