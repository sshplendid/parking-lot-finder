package me.shawn.challenge.parkinglotapi.v1.park.util;

import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;

public class AddressFilter implements ParkInfoFilter {
    @Override
    public boolean filter(ParkInfoDTO parkInfo, String givenAddress) {
        if(parkInfo == null || givenAddress == null || givenAddress.isEmpty()) {
            return false;
        }
        return parkInfo.getAddr().contains(givenAddress);
    }
}
