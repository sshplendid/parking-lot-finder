package me.shawn.challenge.parkinglotapi.v1.park.util;

import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class PriceComparator implements Comparator<ParkInfoDTO> {
    @Override
    public int compare(ParkInfoDTO a, ParkInfoDTO b) {
        return Double.compare(a.getParkingFeePerHour(), b.getParkingFeePerHour());
    }
}
