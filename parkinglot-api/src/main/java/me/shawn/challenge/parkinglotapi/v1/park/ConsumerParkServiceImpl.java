package me.shawn.challenge.parkinglotapi.v1.park;

import lombok.extern.slf4j.Slf4j;
import me.shawn.challenge.parkinglotapi.openapi.OpenApiConsumer;
import me.shawn.challenge.parkinglotapi.openapi.model.OpenApiResponse;
import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;
import me.shawn.challenge.parkinglotapi.v1.park.util.MinPriceParkInfoSorter;
import me.shawn.challenge.parkinglotapi.v1.park.util.ParkInfoSorter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<ParkInfoDTO> getParkInfoByAddress(String address, int rowStartAt, int rowEndAt, String ... arg) {
        // TODO sorter
        MinPriceParkInfoSorter sorter = new MinPriceParkInfoSorter();

        return getParkInfoByAddress(sorter, address, rowStartAt, rowEndAt, arg);
    }
    public List<ParkInfoDTO> getParkInfoByAddress(ParkInfoSorter sorter, String address, int rowStartAt, int rowEndAt, String ... arg) {
        log.info("=> getParkInfoByAddress({}, {}, {}, {}, {})", address, rowStartAt, rowEndAt, arg.length>=1? arg[0]:null, arg.length>=2? arg[1]:null);
        List<ParkInfoDTO> totalList = new ArrayList<>();

        int i = 0;
        int size = 1000;
        while(size == 1000) {
            OpenApiResponse response = openApiConsumer.getParkInfoByAddress((i * 1000 + 1), (i + 1) * 1000, address);
            log.info("\tTry: {}, currentSize: {}, totalSize: {}", i, response.getData().size(), response.getSize());
            size = response.getData().size();

            totalList.addAll(response.getData().stream()
                    .filter(info -> {
                        if(arg.length >= 1 && arg[0] != null) {
                            return arg[0].equals(info.getTel());
                        }
                        return true;
                    })
                    .filter(info -> {
                        if(arg.length >= 2 && arg[1] != null) {
                            return info.getParkingName().contains(arg[1]);
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
