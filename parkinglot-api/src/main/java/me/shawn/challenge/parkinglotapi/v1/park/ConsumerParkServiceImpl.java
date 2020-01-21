package me.shawn.challenge.parkinglotapi.v1.park;

import lombok.extern.slf4j.Slf4j;
import me.shawn.challenge.parkinglotapi.openapi.OpenApiConsumer;
import me.shawn.challenge.parkinglotapi.openapi.model.OpenApiResponse;
import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;
import me.shawn.challenge.parkinglotapi.v1.park.model.CarParkUser;
import me.shawn.challenge.parkinglotapi.v1.park.util.DistanceComparator;
import me.shawn.challenge.parkinglotapi.v1.park.util.ParkInfoSorter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConsumerParkServiceImpl implements ParkService {

    private OpenApiConsumer openApiConsumer;

    public ConsumerParkServiceImpl(OpenApiConsumer openApiConsumer) {
        this.openApiConsumer = openApiConsumer;
    }

    @Override
    public List<ParkInfoDTO> getParkInfoByAddress(String address, int rowStartAt, int rowEndAt, String ... args) {
        String tel = args.length >= 1 ? args[0] : null;
        String parkinName = args.length >= 2 ? args[1] : null;
        final double lat = args.length >= 3 ? Double.parseDouble(args[2]) : -1;
        final double lng = args.length >= 4 ? Double.parseDouble(args[3]) : -1;
        String orderBy = args.length >= 5 ? args[4] : null;

        boolean orderByDistance = "distance".equals(orderBy);

        final Comparator<ParkInfoDTO> distanceComparator = new DistanceComparator(lat, lng);
        final Comparator<ParkInfoDTO> priceComparator = Comparator.comparingDouble(ParkInfoDTO::getParkingFeePerHour);

        ParkInfoSorter sorter = parkInfoStream -> parkInfoStream.sorted((a, b) -> {
            int compared = 0;
            if(orderByDistance) {
                // 거리 먼저
                compared = distanceComparator.compare(a, b);
                if(compared == 0) {
                    compared = priceComparator.compare(a, b);
                }
            } else {
                // 주차비용 먼저
                compared = priceComparator.compare(a, b);
                if(compared == 0) {
                    compared = distanceComparator.compare(a, b);
                }
            }

            return compared;
        });


        return getParkInfoByAddress(address, rowStartAt, rowEndAt, sorter, tel, parkinName);
    }

    @Override
    public List<ParkInfoDTO> getParkInfoByAddress(CarParkUser carParkUser) {
        int rowStartAt = (carParkUser.getPage() - 1) * carParkUser.getPageSize() + 1;
        int rowEndAt = carParkUser.getPage() * carParkUser.getPageSize();
//        return parkService.getParkInfoByAddress(address, rowStartAt, rowEndAt, tel, parkingName, lat, lng, sortType);
        return this.getParkInfoByAddress(carParkUser.getAddress(), rowStartAt, rowEndAt, carParkUser.getTelephone(), carParkUser.getCarParkName(), carParkUser.getLatitude(), carParkUser.getLongitude(), So);
    }

    public List<ParkInfoDTO> getParkInfoByAddress(String address, int rowStartAt, int rowEndAt, ParkInfoSorter sorter, String tel, String parkingName) {
        log.info("=> getParkInfoByAddress({}, {}, {}, {}, {})", address, rowStartAt, rowEndAt, tel, parkingName);
        List<ParkInfoDTO> totalList = new ArrayList<>();

        int i = 0;
        int size = 1000;
        while(size == 1000) {
            OpenApiResponse response = openApiConsumer.getParkInfoByAddress((i * 1000 + 1), (i + 1) * 1000, address);
            log.info("\tTry: {}, currentSize: {}, totalSize: {}", i, response.getData().size(), response.getSize());
            size = response.getData().size();

            totalList.addAll(response.getData().stream()
                    .filter(info -> {
                        if(tel != null) {
                            return tel.equals(info.getTel());
                        }
                        return true;
                    })
                    .filter(info -> {
                        if(parkingName != null) {
                            return info.getParkingName().contains(parkingName);
                        }
                        return true;
                    })
                    .collect(Collectors.toList()));
            ++i;
        }
        log.info("<= OpenApiConsumer: {} requested. filtered size: {}", i, totalList.size());
        return sorter.sort(totalList.stream())
                .collect(Collectors.toList())
                .subList((rowStartAt - 1), rowEndAt > totalList.size() ? totalList.size() : rowEndAt);
    }
}
