package me.shawn.challenge.parkinglotapi.v1.park;

import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;
import me.shawn.challenge.parkinglotapi.v1.park.model.CarParkUser;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/parks")
public class ParkController {

    private ParkService parkService;

    public ParkController(ParkService parkService) {
        this.parkService = parkService;
    }

    @GetMapping("/{address}")
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
        return parkService.getParkInfoByAddress(carParkUser);
    }


}
