package me.shawn.challenge.parkinglotapi.v1.park.util;

import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;

public class AddressParkInfoFilter implements ParkInfoFilter {
    @Override
    public boolean filter(ParkInfoDTO parkInfo, String givenAddress) {
        return false;
    }
}
