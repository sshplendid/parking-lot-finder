package me.shawn.challenge.parkinglotapi.openapi.model;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Alias("park")
@Builder @AllArgsConstructor
@Setter @Getter @NoArgsConstructor @ToString
public class ParkInfoDTO {
    private String parkingName;
    private String addr;
    private String parkingCode;
    private String parkingType;
    private String parkingTypeNm;
    private String operationRule;
    private String operationRuleNm;
    private String tel;
    private String queStatus;
    private String queStatusNm;
    private Double capacity;
    private Double curParking;
    private String curParkingTime;
    private String payYn;
    private String payNm;
    private String nightFreeOpen;
    private String nightFreeOpenNm;
    private String weekdayBeginTime;
    private String weekdayEndTime;
    private String weekendBeginTime;
    private String weekendEndTime;
    private String holidayBeginTime;
    private String holidayEndTime;
    private String syncTime;
    private String saturdayPayYn;
    private String saturdayPayNm;
    private String holidayPayYn;
    private String holidayPayNm;
    private String fulltimeMonthly;
    private String grpParknm;
    private Double rates;
    private Double timeRate;
    private Double addRates;
    private Double addTimeRate;
    private Double busRates;
    private Double busTimeRate;
    private Double busAddTimeRate;
    private Double busAddRates;
    private Double dayMaximum;
    private Double lat;
    private Double lng;
    private String assignCode;
    private String assignCodeNm;

    /**
     * 시간당 예상 요금을 구한다. 만약 추가요금(addRates) 및 추가과금 단위시간(addTimeRate)이 없을 경우, 기본요금(rates) 및 기본 단위시간(timeRate)으로 계산한다.
     * @return 시간당 주차요금
     */
    public double getParkingFeePerHour() {
        double extraFee = addRates > 0 ? addRates : rates;
        double times = (60 - timeRate) / (addTimeRate > 0 ? addTimeRate : timeRate);
        return rates + (times * extraFee);

    }
}
