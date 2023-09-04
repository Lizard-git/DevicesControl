package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sfr.application.devicescontrol.configs.properties.DeviceMessagesProperties;
import sfr.application.devicescontrol.dto.DeviceDTO;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceTypeEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.StatusEntity;
import sfr.application.devicescontrol.enums.TypeMessagesHistory;
import sfr.application.devicescontrol.exceptions.DeviceException;
import sfr.application.devicescontrol.repositories.telbook.device_control.DeviceRepository;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final UserTelbookService userTelbookService;
    private final HistoryService historyService;

    @Autowired
    private DeviceMessagesProperties deviceMessagesProperties;

    public Long getCountAll() {
        return deviceRepository.count();
    }

    public Long getCountByStatus(StatusEntity status) {
        return deviceRepository.countByStatus(status);
    }

    public Map<String, Long> getCountByStatusForDeviceType(DeviceTypeEntity deviceType, List<StatusEntity> allStatusByDevice) {
        Map<String, Long> result = new HashMap<>();
        allStatusByDevice.forEach(status -> result.put(status.getName(), deviceRepository.countByStatusAndType(status, deviceType)));
        return result;
    }

    public List<DeviceEntity> getAll() {
        return deviceRepository.findAll();
    }

    public void save(DeviceDTO deviceDTO, String ipAddress) throws DeviceException, UnknownHostException {
        if (!ObjectUtils.isEmpty(deviceRepository.findAllByTypeAndInventoryNumber(deviceDTO.getType(), deviceDTO.getInventoryNumber()))) {
            historyService.newHistory(
                    deviceMessagesProperties.getAlreadyExistsMessage(),
                    ipAddress,
                    TypeMessagesHistory.Warning,
                    deviceDTO.getType().getName() + " " + deviceDTO.getInventoryNumber()
            );
            throw new DeviceException("Device already created");
        }

        try {
            deviceRepository.save(convert(deviceDTO));
        } catch (Exception e) {
            historyService.newHistory(
                    deviceMessagesProperties.getErrorAddMessage(),
                    ipAddress,
                    TypeMessagesHistory.Error,
                    deviceDTO.getType().getName() + " " + deviceDTO.getInventoryNumber()
            );
            throw new DeviceException("Device save error");
        }
        historyService.newHistory(
                deviceMessagesProperties.getSuccessAddMessage(),
                ipAddress,
                TypeMessagesHistory.Info,
                deviceDTO.getType().getName() + " " + deviceDTO.getInventoryNumber()
        );
    }
    public void change(DeviceDTO deviceDTO, String ipAddress) throws DeviceException, UnknownHostException {
        try {
            deviceRepository.save(convert(deviceDTO));
        } catch (Exception e) {
            historyService.newHistory(
                    deviceMessagesProperties.getErrorChangeMessage(),
                    ipAddress,
                    TypeMessagesHistory.Error,
                    deviceDTO.getType().getName() + " " + deviceDTO.getInventoryNumber()
            );
            throw new DeviceException("Device change error");
        }
        historyService.newHistory(
                deviceMessagesProperties.getSuccessChangeMessage(),
                ipAddress,
                TypeMessagesHistory.Info,
                deviceDTO.getType().getName() + " " + deviceDTO.getInventoryNumber()
        );
    }

    public void delete(DeviceEntity device, String ipAddress) throws UnknownHostException, DeviceException {
        try {
            deviceRepository.delete(device);
        } catch (Exception e) {
            historyService.newHistory(
                    deviceMessagesProperties.getErrorDeleteMessage(),
                    ipAddress,
                    TypeMessagesHistory.Error,
                    device.getType().getName() + " " + device.getInventoryNumber()
            );
            throw new DeviceException("Device change error");
        }
        historyService.newHistory(
                deviceMessagesProperties.getSuccessDeletedMessage(),
                ipAddress,
                TypeMessagesHistory.Info,
                device.getType().getName() + " " + device.getInventoryNumber()
        );
    }

    public DeviceEntity convert(DeviceDTO deviceDTO) {
        String domainNameUsing = null;
       // String domainNameResponsible = null;
        if (!ObjectUtils.isEmpty(deviceDTO.getUserUsing())) {
            domainNameUsing = deviceDTO.getUserUsing().getDomain();
        }
//        if (!ObjectUtils.isEmpty(deviceDTO.getUserResponsible()) ) {
//            domainNameResponsible = deviceDTO.getUserResponsible().getDomain();
//        }
        return DeviceEntity.builder()
                .id(deviceDTO.getId())
                .inventoryNumber(deviceDTO.getInventoryNumber())
                .type(deviceDTO.getType())
                .address(deviceDTO.getAddress())
                .manufacturer(deviceDTO.getManufacturer())
                .model(deviceDTO.getModel())
                .name(deviceDTO.getName())
                .commissioningDate(deviceDTO.getCommissioningDate())
                .serialNumber(deviceDTO.getSerialNumber())
                .cabinet(deviceDTO.getCabinet())
                .warrantyDateBy(deviceDTO.getWarrantyDateBy())
                .warrantyDateWith(deviceDTO.getWarrantyDateWith())
                .status(deviceDTO.getStatus())
                .userUsing(domainNameUsing)
                //.userResponsible(domainNameResponsible)
                .build();
    }

    public DeviceDTO convert( DeviceEntity device) {
        return DeviceDTO.builder()
                .id(device.getId())
                .inventoryNumber(device.getInventoryNumber())
                .type(device.getType())
                .address(device.getAddress())
                .manufacturer(device.getManufacturer())
                .model(device.getModel())
                .name(device.getName())
                .commissioningDate(device.getCommissioningDate())
                .serialNumber(device.getSerialNumber())
                .cabinet(device.getCabinet())
                .warrantyDateBy(device.getWarrantyDateBy())
                .warrantyDateWith(device.getWarrantyDateWith())
                .status(device.getStatus())
                .userUsing(userTelbookService.getByDomain(device.getUserUsing()))
                //.userResponsible(userTelbookService.getByDomain(device.getUserResponsible()))
                .build();
    }
}
