package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SchedulingService {
    @Scheduled(fixedDelayString = "PT2H")
    private void scheduling() {
    }
}
