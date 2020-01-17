package me.shawn.challenge.parkinglotapi.openapi.model;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @Test
    void getParkingFeePerHour() {
        // GIVEN
        // WHEN
        // THEN
    }

    @Test
    void testGetUniqueKey() {
        // GIVEN
        // WHEN
        // THEN
    }

    @Test
    void isParkable_주차되어야하는데_주차가안됨() {
        // GIVEN
        Clock clock = Clock.fixed(Instant.parse("2020-01-17T19:12:24Z"), ZoneId.of("UTC"));
        ParkInfoDTO park = ParkInfoDTO.builder()
                .curParking(0d)
                .capacity(54d)
                .holidayBeginTime("0000")
                .holidayEndTime("0000")
                .weekdayBeginTime("1200")
                .weekdayEndTime("2200")
                .weekendBeginTime("1200")
                .weekendEndTime("1800")
                .build();
//        when(LocalDateTime.now()).thenReturn(LocalDateTime.of(2020, 1, 17, 19, 12, 24));
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println(now);
//        assertThat(now).isEqualToIgnoringSeconds(LocalDateTime.ofInstant(clock.instant(), ZoneId.of("UTC")));

        // WHEN and THEN
        assertThat(park.isParkable()).isTrue();
    }
}