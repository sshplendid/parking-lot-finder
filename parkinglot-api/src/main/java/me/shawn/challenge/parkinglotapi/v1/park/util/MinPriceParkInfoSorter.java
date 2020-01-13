package me.shawn.challenge.parkinglotapi.v1.park.util;

import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MinPriceParkInfoSorter implements ParkInfoSorter {
    @Override
    public Stream<ParkInfoDTO> sort(Stream<ParkInfoDTO> parkInfoStream) {
        return parkInfoStream.sorted(Comparator.comparingDouble(ParkInfoDTO::getParkingFeePerHour));
    }
}
