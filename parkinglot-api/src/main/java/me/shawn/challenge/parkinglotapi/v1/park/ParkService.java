package me.shawn.challenge.parkinglotapi.v1.park;

import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;
import me.shawn.challenge.parkinglotapi.v1.park.model.CarParkUser;

import java.util.List;

public interface ParkService {

    /**
     *  주차장 정보를 주소, 주차장 전화번호, 주차장 이름과 함께 조회한다.
     * @param address 주소
     * @param rowStartAt 조회를 시작할 인덱스 위치
     * @param rowEndAt 조회를 종료할 인덱스 위치
     * @param arg 2개의 인자를 받는다. 첫 번째는 전화번호, 두 번째는 주차장 이름이다.
     * @return 검색조건과 일치하는 결과를 리턴한다.
     */
    List<ParkInfoDTO> getParkInfoByAddress(String address, int rowStartAt, int rowEndAt, String ... arg);

    List<ParkInfoDTO> getParkInfoByAddress(CarParkUser carParkUser);
}
