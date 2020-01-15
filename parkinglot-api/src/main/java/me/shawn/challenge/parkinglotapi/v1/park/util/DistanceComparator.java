package me.shawn.challenge.parkinglotapi.v1.park.util;

import lombok.AllArgsConstructor;
import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;

import java.util.Comparator;
import java.util.stream.Stream;

@AllArgsConstructor
public class DistanceComparator implements Comparator<ParkInfoDTO> {

    private final double lat;
    private final double lng;

    @Override
    public int compare(ParkInfoDTO a, ParkInfoDTO b) {
        final double latDiffOfA = Math.abs(a.getLat() - lat);
        final double lngDiffOfA = Math.abs(a.getLng() - lng);
        final double distanceOfA = Math.sqrt(latDiffOfA*latDiffOfA + lngDiffOfA*lngDiffOfA);

        final double latDiffOfB = Math.abs(b.getLat() - lat);
        final double lngDiffOfB= Math.abs(b.getLng() - lng);
        final double distanceOfB = Math.sqrt(latDiffOfB*latDiffOfB + lngDiffOfB*lngDiffOfB);

        if(distanceOfA > distanceOfB) {
            return 1;
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(this.lat == ((ParkInfoDTO)o).getLat() && this.lng == ((ParkInfoDTO) o).getLng()) {
            return true;
        }
        return false;
    }
}
