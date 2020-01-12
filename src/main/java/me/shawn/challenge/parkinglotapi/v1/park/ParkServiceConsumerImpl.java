package me.shawn.challenge.parkinglotapi.v1.park;

import me.shawn.challenge.parkinglotapi.openapi.OpenApiConsumer;
import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkServiceConsumerImpl implements ParkService {

    private OpenApiConsumer openApiConsumer;

    public ParkServiceConsumerImpl(OpenApiConsumer openApiConsumer) {
        this.openApiConsumer = openApiConsumer;
    }

    @Override
    public List<ParkInfoDTO> getParkInfoByAddress(String address, int rowStartAt, int rowEndAt, String ... arg) {
        return openApiConsumer.getParkInfoByAddress(rowStartAt, rowEndAt, address).getData().stream()
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
                .sorted((o1, o2) -> {
                    if(o1.getParkingFeePerHour() > o2.getParkingFeePerHour()) {
                        return 1;
                    } else if(o1.getParkingFeePerHour() == o2.getParkingFeePerHour()) {
                        return 0;
                    }
                    return -1;
                })
                .collect(Collectors.toList());
    }
}
