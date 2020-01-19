package me.shawn.challenge.parkinglotapi.v1.park.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter @AllArgsConstructor
public class CarParkUser {
    private static final String ALL_ADDRESS = " ";
    private static final String ONLY_NUMBER_PATTERN = "(\\d+)\\-?";

    private LocalDateTime requestedAt;
    @NotEmpty(message = "주소는 필수값입니다.")
    private String address;
    @Min(value = 1, message = "페이지 번호는 1 이상이여야 합니다.")
    private Integer page;
    @Min(value = 1, message = "페이지 사이즈는 1 이상, 1000 이하여야 합니다.")
    @Max(value = 1000, message = "페이지 사이즈는 1 이상, 1000 이하여야 합니다.")
    private Integer pageSize;
    private String carParkName;
    @Pattern(regexp = "((\\d+)\\-?)+", message = "전화번호 형식이 유효하지 않습니다.")
    private String telephone;
    @Min(value = -90, message = "위도의 범위는 90° ~ -90°입니다.")
    @Max(value = 90, message = "위도의 범위는 90° ~ -90°입니다.")
    private Double latitude;
    @Min(value = -180, message = "경도의 범위는 180° ~ -180°입니다.")
    @Max(value = 180, message = "경도의 범위는 180° ~ -180°입니다.")
    private Double longitude;

    CarParkUser() {
        this.requestedAt = LocalDateTime.now();
        this.address = CarParkUser.ALL_ADDRESS;
        this.page = 1;
        this.pageSize = 5;
    }

    CarParkUser(String address) {
        this();
        this.address = address;
    }

    public String getTelephoneOnlyNumber() {
        return this.telephone.replaceAll(CarParkUser.ONLY_NUMBER_PATTERN, "$1");
    }
    public boolean isValidUser() {
        return isValidAddress()
                ;
    }

    private boolean isValidLatitude() {

        return this.latitude == null || (this.latitude <= 90 && this.latitude >= -90);
    }

    private boolean isValidLongitude() {
        return this.longitude == null || (this.longitude <= 180 && this.longitude >= -180);
    }

    private boolean isValidAddress() {
        return address != null && ( this.ALL_ADDRESS.equals(address) || !address.isEmpty() );
    }

    private boolean isValidPage() {
        return this.page == null || this.page > 1;
    }

    private boolean isValidPageSize() {
        return this.pageSize == null || this.pageSize > 1;
    }

    private boolean isValidTelephone() {
        return false;
    }
}
