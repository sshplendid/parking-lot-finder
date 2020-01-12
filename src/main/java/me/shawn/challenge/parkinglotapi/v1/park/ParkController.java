package me.shawn.challenge.parkinglotapi.v1.park;

import me.shawn.challenge.parkinglotapi.openapi.model.ParkInfoDTO;
import org.springframework.web.bind.annotation.*;

import javax.naming.SizeLimitExceededException;
import java.util.List;

@RestController
@RequestMapping("/parks")
public class ParkController {

    private ParkService parkService;

    public ParkController(ParkService parkService) {
        this.parkService = parkService;
    }

    @GetMapping("/{address}")
    public List<ParkInfoDTO> getParkingLotByAddress(@PathVariable String address, @RequestParam(required = false) String tel, @RequestParam(required = false) String parkingName, @RequestParam(required = false, defaultValue = "1") int rowStartAt, @RequestParam(required = false, defaultValue = "30") int rowEndAt) {
        return parkService.getParkInfoByAddress(address, rowStartAt, rowEndAt, tel, parkingName);
    }

}
