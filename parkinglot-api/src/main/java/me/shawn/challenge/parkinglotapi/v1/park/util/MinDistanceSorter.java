package me.shawn.challenge.parkinglotapi.v1.park.util;

import lombok.AllArgsConstructor;
import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;

import java.util.stream.Stream;

@AllArgsConstructor
public class MinDistanceSorter implements ParkInfoSorter {

    private final double lat;
    private final double lng;

    @Override
    public Stream<ParkInfoDTO> sort(Stream<ParkInfoDTO> parkInfoList) {
        return parkInfoList.sorted((a, b) -> {
            final double latDiffOfA = Math.abs(a.getLat() - lat);
            final double lngDiffOfA = Math.abs(a.getLng() - lng);
            final double distanceOfA = Math.sqrt(latDiffOfA*latDiffOfA + lngDiffOfA*lngDiffOfA);

            final double latDiffOfB = Math.abs(b.getLat() - lat);
            final double lngDiffOfB= Math.abs(b.getLng() - lng);
            final double distanceOfB = Math.sqrt(latDiffOfB*latDiffOfB + lngDiffOfB*lngDiffOfB);

            if(distanceOfA < distanceOfB) {
                return 1;
            }
            return -1;
        });
    }
}
