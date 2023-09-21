package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceTypeEntity;
import sfr.application.devicescontrol.exceptions.DeviceTypeException;
import sfr.application.devicescontrol.repositories.telbook.device_control.DeviceTypeRepository;

import java.net.UnknownHostException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DeviceTypeService {
    private final DeviceTypeRepository deviceTypeRepository;

    public List<DeviceTypeEntity> getAll() {
        return deviceTypeRepository.findAll();
    }

    public void save(String name, String ipAddress) throws DeviceTypeException, UnknownHostException {
        if (!ObjectUtils.isEmpty(deviceTypeRepository.findAllByNameContaining(name))) {
            throw new DeviceTypeException("Error. This type already exists");
        }
        try {
            deviceTypeRepository.save(DeviceTypeEntity.builder().name(name).build());
        } catch (Exception e) {
            throw new DeviceTypeException("Error.");
        }
    }
}
