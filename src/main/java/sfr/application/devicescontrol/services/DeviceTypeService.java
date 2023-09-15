package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sfr.application.devicescontrol.configs.properties.DeviceTypeMessagesProperties;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceTypeEntity;
import sfr.application.devicescontrol.enums.TypeMessagesHistory;
import sfr.application.devicescontrol.exceptions.DeviceTypeException;
import sfr.application.devicescontrol.repositories.telbook.device_control.DeviceTypeRepository;

import java.net.UnknownHostException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DeviceTypeService {
    private final DeviceTypeRepository deviceTypeRepository;
    private final HistoryService historyService;

    @Autowired
    private DeviceTypeMessagesProperties deviceTypeMessagesProperties;

    public List<DeviceTypeEntity> getAll() {
        return deviceTypeRepository.findAll();
    }

    public void save(String name, String ipAddress) throws DeviceTypeException, UnknownHostException {
        if (!ObjectUtils.isEmpty(deviceTypeRepository.findAllByNameContaining(name))) {
            historyService.newHistory(
                    deviceTypeMessagesProperties.getAlreadyExistsMessage(),
                    ipAddress,
                    TypeMessagesHistory.Warning,
                    name
            );
            throw new DeviceTypeException("Error. This type already exists");
        }
        try {
            deviceTypeRepository.save(DeviceTypeEntity.builder().name(name).build());
            historyService.newHistory(
                    "Добавил новый тип устройств",
                    ipAddress,
                    TypeMessagesHistory.Info,
                    name
            );
        } catch (Exception e) {
            historyService.newHistory(
                    "Добавил новый тип устройств",
                    ipAddress,
                    TypeMessagesHistory.Error,
                    name
            );
            throw new DeviceTypeException("Error.");
        }
    }
}
