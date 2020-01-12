package me.shawn.challenge.parkinglotapi.v1.park;

import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ParkMapper {
    boolean insertParkInfo(ParkInfoDTO parkInfo);
    List<ParkInfoDTO> getParkInfo(Map<String, Object> query);
}
