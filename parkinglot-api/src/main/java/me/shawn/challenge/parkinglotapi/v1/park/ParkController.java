package me.shawn.challenge.parkinglotapi.v1.park;

import lombok.extern.slf4j.Slf4j;
import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;
import me.shawn.challenge.parkinglotapi.v1.park.model.CarParkUser;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/parks")
public class ParkController {

    private ParkService parkService;
    private ModelMapper modelMapper;

    public ParkController(ParkService parkService, ModelMapper modelMapper) {
        this.parkService = parkService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/temp/{address}")
    public List<ParkInfoDTO> getParkingLotByAddress(
                        @PathVariable String address,
                        @RequestParam(required = false, defaultValue = "1") int page,
                        @RequestParam(required = false, defaultValue = "30") int pagesize,
                        @RequestParam(required = false) String tel,
                        @RequestParam(required = false) String parkingName,
                        @RequestParam(required = false, defaultValue = "-1") String lat,
                        @RequestParam(required = false, defaultValue = "-1") String lng,
                        @RequestParam(required = false, defaultValue = "fee") String sortType) {

        int rowStartAt = (page - 1) * pagesize + 1;
        int rowEndAt = page * pagesize;

        return parkService.getParkInfoByAddress(address, rowStartAt, rowEndAt, tel, parkingName, lat, lng, sortType);
    }

    @GetMapping("/{address}")
    public List<ParkInfoDTO> getParkingLotByAddress2(@Valid CarParkUser carParkUser) {
        log.info("carParkUser: {}", carParkUser);

        return parkService.getParkInfoByAddress(carParkUser);
    }


}
