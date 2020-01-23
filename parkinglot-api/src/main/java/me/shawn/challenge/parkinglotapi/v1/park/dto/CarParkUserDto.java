package me.shawn.challenge.parkinglotapi.v1.park.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.shawn.challenge.parkinglotapi.v1.park.model.CarParkUser;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @ToString
public class CarParkUserDto {
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
    private CarParkUser.ParkSortType sortType;
}
