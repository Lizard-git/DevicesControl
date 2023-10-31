package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.entities.telbook.devices_control.StatusEntity;
import sfr.application.devicescontrol.enums.TypeEntity;
import sfr.application.devicescontrol.repositories.telbook.device_control.StatusRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class StatusService{
    private final StatusRepository statusRepository;

    /**
     * Получает все статусы в программе
     * @return List<StatusEntity>
     */
    public List<StatusEntity> getAll() {
        return statusRepository.findAll();
    }

    /**
     * Для получения статусов по типу сущности (устройства\расходники)
     * @return List<StatusEntity>
     */
    public List<StatusEntity> getAllByType(TypeEntity type) {
        return statusRepository.getAllByType(type);
    }
}
