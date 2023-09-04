package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.entities.telbook.devices_control.SettlementsEntity;
import sfr.application.devicescontrol.repositories.telbook.device_control.SettlementsRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SettlementsService {
    private final SettlementsRepository settlementsRepository;

    /**
     * Получает все населенные пункты из базы данных
     * @return List<SettlementsEntity>
     */
    public List<SettlementsEntity> getAllSettlements() {return settlementsRepository.findAll();}
}
