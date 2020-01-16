package me.shawn.challenge.parkinglotapi.v1.park.util;

import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;

import java.util.stream.Stream;

public interface ParkInfoSorter {
    public Stream<ParkInfoDTO> sort(Stream<ParkInfoDTO> parkStream);
}
