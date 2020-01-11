package me.shawn.challenge.parkinglotapi.openapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
}
