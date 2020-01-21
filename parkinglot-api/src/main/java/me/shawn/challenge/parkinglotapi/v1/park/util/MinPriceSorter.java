package me.shawn.challenge.parkinglotapi.v1.park.util;

import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;

import java.util.Comparator;
import java.util.stream.Stream;

public class MinPriceSorter implements ParkInfoSorter {
    private Comparator<ParkInfoDTO> distanceComparator;
    private Comparator<ParkInfoDTO> priceComparator;

    public MinPriceSorter(Comparator<ParkInfoDTO> distanceComparator, Comparator<ParkInfoDTO> priceComparator) {
        this.distanceComparator = distanceComparator;
        this.priceComparator = priceComparator;
    }

    @Override
    public Stream<ParkInfoDTO> sort(Stream<ParkInfoDTO> parkStream) {
        return parkStream.sorted((a, b) -> {
            int compared =  priceComparator.compare(a, b);
            if(compared == 0) {
                compared = distanceComparator.compare(a, b);
            }

            return compared;
        });
    }
}
