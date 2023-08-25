package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceTypeEntity;
import sfr.application.devicescontrol.repositories.telbook.device_control.DeviceTypeRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DeviceTypeService {
    private final DeviceTypeRepository deviceTypeRepository;

    public List<DeviceTypeEntity> getAll() {
        return deviceTypeRepository.findAll();
    }
}
