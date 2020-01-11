package me.shawn.challenge.parkinglotapi.v1.park;

import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;

import java.util.List;

public interface ParkService {

    List<ParkInfoDTO> getParkInfoByAddress(String address, int rowStartAt, int rowEndAt, String ... arg);
}
