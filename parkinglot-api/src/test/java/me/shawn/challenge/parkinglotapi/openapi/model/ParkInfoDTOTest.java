package me.shawn.challenge.parkinglotapi.openapi.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ParkInfoDTOTest {

    @Test
    void getParkingFeePerHour_withRatesAndAddRates() {
        // GIVEN
        ParkInfoDTO parkInfo = ParkInfoDTO.builder()
                .rates(200d)
                .timeRate(5d)
                .addRates(200d)
                .addTimeRate(5d)
                .build();

        // WHEN
        Double parkingFeePerHour = parkInfo.getParkingFeePerHour();

        // THEN
        assertThat(parkingFeePerHour).isEqualTo(2400d);
    }
    @Test
    void getParkingFeePerHour_withoutAddRates() {
        // GIVEN
        ParkInfoDTO parkInfo = ParkInfoDTO.builder()
                .rates(200d)
                .timeRate(5d)
                .addRates(0d)
                .addTimeRate(0d)
                .build();

        // WHEN
        Double parkingFeePerHour = parkInfo.getParkingFeePerHour();

        // THEN
        assertThat(parkingFeePerHour).isEqualTo(2400d);
    }

    @Test
    void getUniqueKey() {
        // GIVEN
        ParkInfoDTO given = ParkInfoDTO.builder()
                .parkingCode("1568867")
                .lat(37.5550823)
                .lng(126.90037915)
                .build();

        // WHEN
        String uniqueKey = given.getUniqueKey();

        // THEN
        System.out.println(uniqueKey);
        assertThat(uniqueKey).isEqualTo("1568867-037.5550823000-126.9003791500");
    }
}