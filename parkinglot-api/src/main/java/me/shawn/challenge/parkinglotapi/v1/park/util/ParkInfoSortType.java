package me.shawn.challenge.parkinglotapi.v1.park.util;

public enum ParkInfoSortType {
    FEE, DISTANCE;

    public static ParkInfoSortType resolve(String source) {
        for (ParkInfoSortType message : values()) {
            if (message.name().equals(source.toUpperCase())) {
                return message;
            }
        }
        return null;
    }
}
