package me.shawn.challenge.parkinglotapi.v1.park.util;

import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;

public interface ParkInfoFilter {
    boolean filter(ParkInfoDTO parkInfo, String givenAddress);
}
