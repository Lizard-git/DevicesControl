package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.entities.telbook.devices_control.ManufacturerEntity;
import sfr.application.devicescontrol.enums.TypeEntity;
import sfr.application.devicescontrol.repositories.telbook.device_control.ManufacturerRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public List<ManufacturerEntity> getAll() {
        return manufacturerRepository.findAll();
    }

    public List<ManufacturerEntity> getAllByType(TypeEntity type) {
        return manufacturerRepository.findAllByTypeColumn(type);
    }
}
