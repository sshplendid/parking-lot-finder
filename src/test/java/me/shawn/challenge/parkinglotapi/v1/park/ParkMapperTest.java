package me.shawn.challenge.parkinglotapi.v1.park;

import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;
import me.shawn.challenge.parkinglotapi.v1.park.mapper.ParkMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({"local"})
@Transactional
@SpringBootTest
class ParkMapperTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ParkMapper parkMapper;

    @BeforeEach
    void setUp() {

    }

    @Test
    void insertParkInfo() {
        // GIVEN
        String addr = "마포";
        Map<String, Object> map = new HashMap<>();
        map.put("addr", addr);

        ParkInfoDTO parkInfo = ParkInfoDTO.builder()
                .addr("마포구 합정동")
                .rates(200d)
                .timeRate(5d)
                .addRates(200d)
                .addTimeRate(5d)
                .tel("02-300-1234")
                .build();

        // WHEN
        parkMapper.insertParkInfo(parkInfo);

        // THEN
        assertThat(parkMapper.getParkInfo(map)).isNotEmpty();
    }

    @Test
    void getParkInfo() {
        // GIVEN
        String addr = "마포";
        Map<String, Object> map = new HashMap<>();
        map.put("addr", addr);

        // WHEN
        List<ParkInfoDTO> list = parkMapper.getParkInfo(map);

        // THEN
        assertThat(list).isNotNull();
    }
}