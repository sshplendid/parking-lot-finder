package me.shawn.challenge.parkinglotapi.v1.park.util;

import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddressFilterTest {

    private AddressFilter filter;
    @BeforeEach
    void setUp() {
        this.filter = new AddressFilter();
    }

    @Test
    void filter_pass() {
        // GIVEN
        String givenAddress = "망원";
        ParkInfoDTO parkInfo = ParkInfoDTO.builder()
                .addr("마포구 망원동")
                .build();

        // WHEN and THEN
        assertThat(filter.filter(parkInfo, givenAddress)).isTrue();
    }

    @Test
    void filter_fail() {
        // GIVEN
        String givenAddress = "서초동";
        ParkInfoDTO parkInfo = ParkInfoDTO.builder()
                .addr("서대문구 연희동")
                .build();

        // WHEN and THEN
        assertThat(filter.filter(parkInfo, givenAddress)).isFalse();
    }

    @Test
    void filter_fail_parmeters_are_null() {
        // GIVEN
        String givenAddress = "";
        ParkInfoDTO parkInfo = ParkInfoDTO.builder()
                .addr("서대문구 연희동")
                .build();

        // WHEN and THEN
        assertThat(filter.filter(parkInfo, givenAddress)).isFalse();
    }
}