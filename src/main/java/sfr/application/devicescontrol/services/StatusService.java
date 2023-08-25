package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.entities.telbook.devices_control.StatusEntity;
import sfr.application.devicescontrol.enums.TypeEntity;
import sfr.application.devicescontrol.repositories.telbook.device_control.StatusRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class StatusService{
    private final StatusRepository statusRepository;

    public List<StatusEntity> getAll() {
        return statusRepository.findAll();
    }

    public List<StatusEntity> getAllByType(TypeEntity type) {
        return statusRepository.getAllByType(type);
    }
}
