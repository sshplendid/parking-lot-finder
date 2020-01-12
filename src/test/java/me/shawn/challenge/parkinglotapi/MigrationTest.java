package me.shawn.challenge.parkinglotapi;

import me.shawn.challenge.parkinglotapi.openapi.OpenApiConsumer;
import me.shawn.challenge.parkinglotapi.openapi.model.OpenApiResponse;
import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;
import me.shawn.challenge.parkinglotapi.v1.park.ParkMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({"mysql"})
@SpringBootTest
@Transactional
public class MigrationTest {

    private static final Logger log = LoggerFactory.getLogger(MigrationTest.class);

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ParkMapper parkMapper;

    @Autowired
    OpenApiConsumer openApiConsumer;

    @Disabled
    @Test
    void migration() {
        StopWatch stopWatch = new StopWatch();
        int i = 0;
        int count = 0;
        stopWatch.start();
        while(i < 15) {
            OpenApiResponse response = openApiConsumer.getParkInfoByAddress(i * 1000 + 1, (i + 1) * 1000, null);
            List<ParkInfoDTO> data = response.getData();
            log.info("response size: {}", data.size());

            data.forEach(park -> {
                parkMapper.insertParkInfo(park);
            });
            count += data.size();
            ++i;
        }
        stopWatch.stop();
        log.info("TotalMillis: {}ms", stopWatch.getTotalTimeMillis());

        assertThat(count).isEqualTo(14636);
    }
}
