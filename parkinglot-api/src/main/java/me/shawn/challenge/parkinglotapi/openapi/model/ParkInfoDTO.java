package me.shawn.challenge.parkinglotapi.openapi.model;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
        if(rates == 0) {
            return 0;
        }
        double extraFee = addRates > 0 ? addRates : rates;
        double times = (60 - timeRate) / (addTimeRate > 0 ? addTimeRate : timeRate);
        return rates + (times * extraFee);

    }

    /**
     * 주차장 정보의 고유번호를 리턴한다. {주차장코드}-{위도}-{경도} 형식으로 나타낸다.
     * @return
     */
    public String getUniqueKey() {
        return String.format("%s-%014.10f-%014.10f", this.parkingCode, this.lat, this.lng);
    }

    /**
     * 인자로 주어진 일시에 주차가능여부를 나타낸다.
     * @return
     */
    public boolean isParkable() {
        LocalDateTime parkDateTime = LocalDateTime.now();
        boolean isHoliday = false;
        if(parkDateTime.getDayOfWeek() == DayOfWeek.SUNDAY && parkDateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
            isHoliday = true;
        }

        if(isHoliday) return isParkableHoliday(parkDateTime);


        return isParkableWeekday(parkDateTime);
    }

    private boolean isParkableWeekday(LocalDateTime parkDateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmm");
        int openHour = Integer.parseInt(this.weekdayBeginTime.substring(0, 2));
        int closeHour = Integer.parseInt(this.weekdayEndTime.substring(0, 2));
        LocalTime openAt = LocalTime.of(openHour > 23? 0 : openHour, Integer.parseInt(this.weekdayBeginTime.substring(2)));
        LocalTime closeAt = closeHour == 24 ? LocalTime.MAX : LocalTime.of(closeHour, Integer.parseInt(this.weekdayEndTime.substring(2)));

        return this.isEnoughCapacity() && parkDateTime.toLocalTime().isAfter(openAt) && parkDateTime.toLocalTime().isBefore(closeAt);
    }

    private boolean isParkableHoliday(LocalDateTime parkDateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmm");
        int openHour = Integer.parseInt(this.weekendBeginTime.substring(0, 2));
        int closeHour = Integer.parseInt(this.weekendEndTime.substring(0, 2));
        LocalTime openAt = LocalTime.of(openHour > 23? 0 : openHour, Integer.parseInt(this.weekendBeginTime.substring(2)));
        LocalTime closeAt = closeHour == 24 ? LocalTime.MAX : LocalTime.of(closeHour, Integer.parseInt(this.weekendEndTime.substring(2)));

        return this.isEnoughCapacity() && parkDateTime.toLocalTime().isAfter(openAt) && parkDateTime.toLocalTime().isBefore(closeAt);
    }

    private boolean isEnoughCapacity() {
        return (this.capacity - this.curParking) > 0;
    }
}
