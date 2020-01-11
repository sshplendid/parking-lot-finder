package me.shawn.challenge.parkinglotapi.openapi;

import me.shawn.challenge.parkinglotapi.openapi.model.OpenApiResponse;
import me.shawn.challenge.parkinglotapi.openapi.model.OpenApiStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import javax.naming.SizeLimitExceededException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class OpenApiConsumerTest {
    private static final Logger log = LoggerFactory.getLogger(OpenApiConsumerTest.class);

    private OpenApiConsumer openApiConsumer;
    private RestTemplate restTemplate;
    private static final String END_POINT = "http://openapi.seoul.go.kr:8088";
    private static final String TOKEN = "6a756259796a756d32304559665677";

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        openApiConsumer = new OpenApiConsumer(restTemplate, TOKEN, END_POINT);
    }

    @Test
    void apiFetchTest() {
        // GIVEN
        int rowStartAt = 1;
        int rowEndAt = 5;
        String address = "마포";

        // WHEN
        OpenApiResponse response = openApiConsumer.fetchApiData(OpenApiConsumer.PARK_API_NAME, rowStartAt, rowEndAt, address);

        // THEN
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getApiStatus()).isEqualTo(OpenApiStatus.OK);
        assertThat(response.getData()).isNotEmpty();
        assertThat(response.getData()).hasSizeLessThanOrEqualTo((rowEndAt - rowStartAt + 1));
    }

    @Test
    void maxSizeTest() {
        // GIVEN
        String address = " ";
        StopWatch stopWatch = new StopWatch("maxSizeTest");

        // WHEN
        int size = 1000;
        int i = 0;
        int actualTotalSize = 0;
        int expectedTotalSize = -1;

        while(size == 1000) {
            int rowStartAt = i * 1000 + 1;
            int rowEndAt = i * 1000 + 1000;

            stopWatch.start("sub-"+ (i+1));
            OpenApiResponse response =
                    openApiConsumer.fetchApiData(OpenApiConsumer.PARK_API_NAME, rowStartAt, rowEndAt, address);
            stopWatch.stop();

            // THEN
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);

            if(response.getApiStatus() != OpenApiStatus.OK) {
                log.error("ApiStatus is not OK.: {}", response.getApiStatus());
                break;
            }

            assertThat(response.getData()).isNotEmpty();
            expectedTotalSize = response.getSize();
            actualTotalSize += response.getData().size();

            log.info("try seq {}: real size -> {}", i+1, response.getData().size());

            size = response.getData().size();
            ++i;
        }

        log.info("---------------------------");
        log.info("total time millis: {}", stopWatch.getTotalTimeMillis());
        log.info("누적 요청 데이터: {}", actualTotalSize);
        log.info(stopWatch.prettyPrint());

        assertThat(actualTotalSize).isEqualTo(expectedTotalSize);
    }

    @Test
    void parkingCodeTest() {
        // GIVEN
        String parkingCode = "1033754";

        // WHEN
        OpenApiResponse response = openApiConsumer.getParkInfoByParkingCode(parkingCode);

        // THEN
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getApiStatus()).isEqualTo(OpenApiStatus.OK);
        assertThat(response.getData()).isNotNull();
        assertThat(response.getData()).isNotEmpty();
        assertThat(response.getData()).hasSize(1);
        assertThat(response.getData().get(0)).hasFieldOrPropertyWithValue("parkingCode", parkingCode);
        log.info("parkInfo: {}", response.getData().get(0));
    }

    @Test
    void serverErrorTest() {
        // GIVEN
        int rowStartAt = 1;
        int rowEndAt = 1;
        String address = "마포";
        String apiServiceName = "WrongService";

        // WHEN
        OpenApiResponse response = openApiConsumer.fetchApiData(apiServiceName, rowStartAt, rowEndAt, address);

        // THEN
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getApiStatus()).isEqualTo(OpenApiStatus.SERVER_ERROR);
    }

    @Test
    void noDataTest() {
        // GIVEN
        int rowStartAt = 1;
        int rowEndAt = 1;
        String address = "화성";

        // WHEN
        OpenApiResponse response = openApiConsumer.fetchApiData(OpenApiConsumer.PARK_API_NAME, rowStartAt, rowEndAt, address);

        // THEN
        assertThat(response.getApiStatus()).isEqualTo(OpenApiStatus.NO_DATA);
    }

    @Test
    void wrongIndexRangeTest() {
        // GIVEN
        int rowStartAt = 2;
        int rowEndAt = 1;
        String address = "마포";

        // WHEN and THEN
        assertThrows(IllegalArgumentException.class, () -> {
            OpenApiResponse response = openApiConsumer.getParkInfoByAddress(rowStartAt, rowEndAt, address);
        });

    }

    @Test
    void sizeLimitExceededExceptionAtClientSideTest() {
        // GIVEN
        int rowStartAt = 1;
        int rowEndAt = 1001;
        String address = "서울";

        // WHEN and THEN
        assertThrows(IllegalArgumentException.class, () -> {
            OpenApiResponse response = openApiConsumer.fetchApiData(OpenApiConsumer.PARK_API_NAME, rowStartAt, rowEndAt, address);
        });
    }
}
