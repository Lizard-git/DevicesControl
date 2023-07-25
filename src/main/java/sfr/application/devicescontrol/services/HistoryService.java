package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.repositories.telbook.device_control.HistoryRepository;

@Service
@AllArgsConstructor
@Slf4j
public class HistoryService {
    private final HistoryRepository historyRepository;
}
