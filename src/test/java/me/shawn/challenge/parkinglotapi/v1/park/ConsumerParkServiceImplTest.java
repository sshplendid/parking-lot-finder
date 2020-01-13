package me.shawn.challenge.parkinglotapi.v1.park;

import me.shawn.challenge.parkinglotapi.openapi.OpenApiConsumer;
import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import javax.naming.SizeLimitExceededException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ConsumerParkServiceImplTest {
    private static final Logger log = LoggerFactory.getLogger(ConsumerParkServiceImplTest.class);
    private StopWatch stopWatch;

    private ParkService parkService;

    @BeforeEach
    void setUp() {
        String endpoint = "http://openapi.seoul.go.kr:8088";
        String token = "6a756259796a756d32304559665677";
        RestTemplate restTemplate = new RestTemplate();

        OpenApiConsumer openApiConsumer = new OpenApiConsumer(restTemplate, token, endpoint);
        parkService = new ConsumerParkServiceImpl(openApiConsumer);

        stopWatch = new StopWatch("junit");
    }

    @Test
    void getParkInfoByAddress_마포구() {
        // GIVEN
        String address = "마포구";
        stopWatch.start();

        // WHEN
        List<ParkInfoDTO> parkInfo = parkService.getParkInfoByAddress(address, 1, 5);
        stopWatch.stop();

        // THEN
        assertThat(parkInfo).isNotEmpty();
        assertThat(parkInfo).hasSizeGreaterThan(0);
        log.info("list({}): {}", parkInfo.size(), parkInfo);
        log.info("totalTimeMillis: {} ms", stopWatch.getTotalTimeMillis());
    }

    @Test
    void getParkInfoByAddress_망원동() {
        // GIVEN
        String address = "망원동";
        stopWatch.start();

        // WHEN
        List<ParkInfoDTO> parkInfo = parkService.getParkInfoByAddress(address, 1, 5);
        stopWatch.stop();

        // THEN
        assertThat(parkInfo).isNotEmpty();
        assertThat(parkInfo).hasSizeGreaterThan(0);
        log.info("list({}): {}", parkInfo.size(), parkInfo);
        log.info("list({}): {}", parkInfo.size(), parkInfo);
        log.info("totalTimeMillis: {} ms", stopWatch.getTotalTimeMillis());
    }

    @Test
    void getParkInfoByAddressAndTel() {
        // GIVEN
        String address = "마포";
        String tel = "02-300-5052";
        stopWatch.start();

        // WHEN
        List<ParkInfoDTO> parkInfo = parkService.getParkInfoByAddress(address, 1, 5, tel);
        stopWatch.stop();;

        // THEN
        assertThat(parkInfo).isNotEmpty();
        assertThat(parkInfo).hasSizeGreaterThan(0);
        assertThat(parkInfo).allMatch(parkInfoDTO -> tel.equals(parkInfoDTO.getTel()));
        log.info("list({}): {}", parkInfo.size(), parkInfo);
        log.info("totalTimeMillis: {} ms", stopWatch.getTotalTimeMillis());
    }

    @Test
    void getParkInfoByAddressAndParkingLotName() {
        // GIVEN
        String address = "마포";
        String parkingLotName = "공영주차장";
        stopWatch.start();

        // WHEN
        List<ParkInfoDTO> parkInfo = parkService.getParkInfoByAddress(address, 1, 5, null, parkingLotName);
        stopWatch.stop();;

        // THEN
        assertThat(parkInfo).isNotEmpty();
        assertThat(parkInfo).hasSizeGreaterThan(0);
        assertThat(parkInfo).allMatch(parkInfoDTO -> parkInfoDTO.getParkingName().contains(parkingLotName));
        log.info("list({}): {}", parkInfo.size(), parkInfo);
        log.info("totalTimeMillis: {} ms", stopWatch.getTotalTimeMillis());
    }

    @Test
    void getParkInfoByAddressAndTelAndParkingLotName() {
        // GIVEN
        int rowStartAt = 1;
        int rowEndAt = 1000;
        String address = "마포";
        String tel = "02-300-5052";
        String parkingLotName = "공영주차장";
        stopWatch.start();

        // WHEN
        List<ParkInfoDTO> parkInfo = parkService.getParkInfoByAddress(address, rowStartAt, rowEndAt, tel, parkingLotName);
        stopWatch.stop();;

        // THEN
        assertThat(parkInfo).isNotEmpty();
        assertThat(parkInfo).hasSizeGreaterThan(0);
        assertThat(parkInfo).allMatch(parkInfoDTO -> tel.equals(parkInfoDTO.getTel()) && parkInfoDTO.getParkingName().contains(parkingLotName));
        log.info("list({}): {}", parkInfo.size(), parkInfo);
        log.info("totalTimeMillis: {} ms", stopWatch.getTotalTimeMillis());
    }

    @Test
    void listOrderTest() {
        // GIVEN
        int rowStartAt = 1;
        int rowEndAt = 10;
        String address = "마포";
        stopWatch.start();

        // WHEN
        List<ParkInfoDTO> parkInfo = parkService.getParkInfoByAddress(address, rowStartAt, rowEndAt);
        stopWatch.stop();;
        List<ParkInfoDTO> orderedParkInfoList = parkInfo.stream().sorted((o1, o2) -> {
            if(o1.getParkingFeePerHour() > o2.getParkingFeePerHour()) {
                return 1;
            } else if(o1.getParkingFeePerHour() == o2.getParkingFeePerHour()) {
                return 0;
            }
            return -1;
        }).collect(Collectors.toList());

        // THEN
        assertThat(parkInfo).isNotEmpty();
        assertThat(parkInfo).hasSizeGreaterThan(0);
        assertThat(orderedParkInfoList).isEqualTo(parkInfo);
        log.info("list({}): {}", parkInfo.size(), parkInfo);
        log.info("totalTimeMillis: {} ms", stopWatch.getTotalTimeMillis());
        log.info("=== 정렬순서 ===");
        orderedParkInfoList.forEach(p -> {
            log.info("{}: {}", p.getParkingName(), p.getParkingFeePerHour());
        });
    }

    @Test
    void arrayBoundsTest() {
        // GIVEN
        int rowStartAt = 1;
        int rowEndAt = 1000;
        String address = "강남";
        String parkingName = "공영주차장";

        // WHEN
        List<ParkInfoDTO> list = parkService.getParkInfoByAddress(address, rowStartAt, rowEndAt, null, parkingName);

        // THEN
        assertThat(list).isNotEmpty();
        assertThat(list).hasSizeGreaterThanOrEqualTo(2);
    }

    @AfterEach
    void tearDown() {
    }
}