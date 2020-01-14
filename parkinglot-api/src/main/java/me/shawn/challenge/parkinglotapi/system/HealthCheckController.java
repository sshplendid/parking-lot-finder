package me.shawn.challenge.parkinglotapi.system;

import me.shawn.challenge.parkinglotapi.model.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @GetMapping
    public CommonResponse<String> health() {
        return CommonResponse.<String>builder()
                .message("OK")
                .build();
    }
}
