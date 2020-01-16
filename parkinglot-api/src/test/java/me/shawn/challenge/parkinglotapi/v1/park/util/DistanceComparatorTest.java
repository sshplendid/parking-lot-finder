package me.shawn.challenge.parkinglotapi.v1.park.util;

import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DistanceComparatorTest {

    private DistanceComparator distanceComparator;

    @BeforeEach
    void setUp() {
        distanceComparator = new DistanceComparator(37.5546837, 126.9102778);
    }

    @Test
    void compare() {
        // GIVEN
        ParkInfoDTO saroga = ParkInfoDTO.builder()
                .parkingName("연희사러가쇼핑센터앞(구)")
                .lat(37.56667593)
                .lng(126.92963507)
                .build();
        ParkInfoDTO chongiwa = ParkInfoDTO.builder()
                .parkingName("청기와 주차장")
                .lat(37.5546837)
                .lng(126.9102778)
                .build();
        ParkInfoDTO nosang = ParkInfoDTO.builder()
                .parkingName("성암로 관광버스 노상주차장(시)")
                .lat(37.58159316)
                .lng(126.89170259)
                .build();

        List<ParkInfoDTO> list = new ArrayList<>();
        list.add(saroga);
        list.add(chongiwa);
        list.add(nosang);

        // WHEN
        List sortedList = list.stream()
                .sorted(distanceComparator)
                .collect(Collectors.toList());

        // THEN
        assertThat(sortedList).first().isEqualTo(chongiwa);
        assertThat(sortedList).last().isEqualTo(nosang);
    }
}