package me.shawn.challenge.parkinglotapi.config;

import me.shawn.challenge.parkinglotapi.v1.park.util.ParkInfoSortType;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, ParkInfoSortType> {
    @Override
    public ParkInfoSortType convert(String source) {
        return ParkInfoSortType.resolve(source);
    }
}
