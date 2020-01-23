package me.shawn.challenge.parkinglotapi.v1.park.model;

import lombok.*;

import java.time.LocalDateTime;

// TODO DDD: Setter 제거하고 DTO 객체를 사용하는 방법을 고민.
@Setter
@Builder @ToString
@Getter @AllArgsConstructor
public class CarParkUser {
    private static final String ALL_ADDRESS = " ";
    private static final String ONLY_NUMBER_PATTERN = "(\\d+)-?";

    private LocalDateTime requestedAt;
    private String address;
    private Integer page;
    private Integer pageSize;
    private String carParkName;
    private String telephone;
    private Double latitude;
    private Double longitude;
    private ParkSortType sortType;

    CarParkUser() {
        this.requestedAt = LocalDateTime.now();
        this.address = CarParkUser.ALL_ADDRESS;
        this.page = 1;
        this.pageSize = 5;
        this.sortType = ParkSortType.FEE;

        // 서울 기본
        this.latitude = 37.566;
        this.longitude = 126.9784;
    }

    CarParkUser(String address) {
        this();
        this.address = address;
    }

    public CarParkUser(LocalDateTime requestedAt, String address, int page, int pageSize, String carParkName, String telephone, double latitude, double longitude) {
        this.requestedAt = requestedAt;
        this.address = address;
        this.page = page;
        this.pageSize = pageSize;
        this.sortType = ParkSortType.FEE;
        this.carParkName = carParkName;
        this.telephone = telephone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getOnlyTelephoneNumber() {
        return this.telephone.replaceAll(CarParkUser.ONLY_NUMBER_PATTERN, "$1");
    }

    public enum ParkSortType {
        FEE, DISTANCE
    }

    public static boolean isOrderByMinPrice(CarParkUser carParkUser) {
        return carParkUser.getSortType() == ParkSortType.FEE;
    }

}
