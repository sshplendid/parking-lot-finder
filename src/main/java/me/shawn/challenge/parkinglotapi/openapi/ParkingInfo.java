package me.shawn.challenge.parkinglotapi.openapi;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class ParkingInfo {
    public static final String API_NAME = "GetParkInfo";

    private String PARKING_NAME;
    private String ADDR;
    private String PARKING_CODE;
    private String PARKING_TYPE;
    private String PARKING_TYPE_NM;
    private String OPERATION_RULE;
    private String OPERATION_RULE_NM;
    private String TEL;
    private String QUE_STATUS;
    private String QUE_STATUS_NM;
    private Double CAPACITY;
    private Double CUR_PARKING;
    private String CUR_PARKING_TIME;
    private String PAY_YN;
    private String PAY_NM;
    private String NIGHT_FREE_OPEN;
    private String NIGHT_FREE_OPEN_NM;
    private String WEEKDAY_BEGIN_TIME;
    private String WEEKDAY_END_TIME;
    private String WEEKEND_BEGIN_TIME;
    private String WEEKEND_END_TIME;
    private String HOLIDAY_BEGIN_TIME;
    private String HOLIDAY_END_TIME;
    private String SYNC_TIME;
    private String SATURDAY_PAY_YN;
    private String SATURDAY_PAY_NM;
    private String HOLIDAY_PAY_YN;
    private String HOLIDAY_PAY_NM;
    private String FULLTIME_MONTHLY;
    private String GRP_PARKNM;
    private Double RATES;
    private Double TIME_RATE;
    private Double ADD_RATES;
    private Double ADD_TIME_RATE;
    private Double BUS_RATES;
    private Double BUS_TIME_RATE;
    private Double BUS_ADD_TIME_RATE;
    private Double BUS_ADD_RATES;
    private Double DAY_MAXIMUM;
    private Double LAT;
    private Double LNG;
    private String ASSIGN_CODE;
    private String ASSIGN_CODE_NM;
}
