package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sfr.application.devicescontrol.dto.DeviceDTO;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceTypeEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.StatusEntity;
import sfr.application.devicescontrol.enums.TypeEntity;
import sfr.application.devicescontrol.exceptions.DeviceException;
import sfr.application.devicescontrol.repositories.telbook.device_control.DeviceRepository;
import sfr.application.devicescontrol.repositories.telbook.device_control.DeviceTypeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final DeviceTypeRepository deviceTypeRepository;
    private final StatusService statusService;
    private final UserTelbookService userTelbookService;
    public Long getCountAll() {
        return deviceRepository.count();
    }

    public Long getCountByStatus(StatusEntity status) {
        return deviceRepository.countByStatus(status);
    }

    public List<StatusEntity> getAllStatusByDevices() {
        return statusService.getAllByType(TypeEntity.device);
    }

    public List<DeviceTypeEntity> getAllTypesDevices() {
        return deviceTypeRepository.findAll();
    }

    public Map<String, Long> getCountByStatusForDeviceType(DeviceTypeEntity deviceType) {
        Map<String, Long> result = new HashMap<>();
        List<StatusEntity> allStatus = getAllStatusByDevices();
        allStatus.forEach(status -> result.put(status.getName(), deviceRepository.countByStatusAndType(status, deviceType)));
        return result;
    }

    public List<DeviceEntity> getAll() {
        return deviceRepository.findAll();
    }

    public void save(DeviceDTO deviceDTO) throws DeviceException {

        if (ObjectUtils.isEmpty(deviceRepository.findAllByTypeAndInventoryNumber(deviceDTO.getType(), deviceDTO.getInventoryNumber()))) {
            try {
                deviceRepository.save(convert(deviceDTO));
            } catch (Exception e) {
                throw new DeviceException("Device save error !");
            }
        } else {
            throw new DeviceException("Device already created !");
        }

    }
    public void change(DeviceDTO deviceDTO) throws DeviceException {
        try {
            deviceRepository.save(convert(deviceDTO));
        } catch (Exception e) {
            throw new DeviceException("Device change error !");
        }
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
