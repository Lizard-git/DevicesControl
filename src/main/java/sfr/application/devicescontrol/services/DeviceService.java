package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sfr.application.devicescontrol.dto.DeviceDTO;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceTypeEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.StatusEntity;
import sfr.application.devicescontrol.entities.telbook.prov_ter_org.UsersTelbookEntity;
import sfr.application.devicescontrol.exceptions.DeviceException;
import sfr.application.devicescontrol.repositories.telbook.device_control.DeviceRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final UserTelbookService userTelbookService;

    /**
     * Получает кол-во устройств зарегистрированных в базе данных
     * @return Кол-во устройств
     */
    public Long getCountAll() {
        return deviceRepository.count();
    }

    /**
     * Получает кол-во устройств зарегистрированных в базе данных по определенному статусу
     * @param status - статус устройств
     * @return Кол-во устройств
     */
    public Long getCountByStatus(StatusEntity status) {
        return deviceRepository.countByStatus(status);
    }

    /**
     * Получает кол-во устройств зарегистрированных в базе
     * данных по всем переданным статусам и определенному типу устройства
     * @param deviceType - тип устройства
     * @param allStatusByDevice - список статусов
     * @return Map<String, Long>, где будут указанно кол-во устройств определенного статуса одного, указанного типа
     * (Map<"Статус", кол-во>).
     */
    public Map<String, Long> getCountByStatusForDeviceType(DeviceTypeEntity deviceType, List<StatusEntity> allStatusByDevice) {
        Map<String, Long> result = new HashMap<>();
        allStatusByDevice.forEach(status -> result.put(status.getName(), deviceRepository.countByStatusAndType(status, deviceType)));
        return result;
    }

    /**
     * Получает все устройства из базы данных
     * @return List<DeviceEntity>
     */
    public List<DeviceEntity> getAll() {
        return deviceRepository.findAll();
    }

    /**
     * Сохраняет новое устройство в базу данных
     * @param deviceDTO - dto устройства
     * @param ipAddress - ip адрес
     * @throws DeviceException - Ошибка! (устройство уже существует\неизвестная ошибка)
     */
    public void save(DeviceDTO deviceDTO, String ipAddress) throws DeviceException {
        if (deviceRepository.existsByTypeAndInventoryNumber(deviceDTO.getType(), deviceDTO.getInventoryNumber())) {
            throw new DeviceException("Save error. Device already created.");
        }
        try {
            deviceRepository.save(convert(deviceDTO));
        } catch (Exception e) {
            throw new DeviceException("Device save error.");
        }
    }

    /**
     * Изменяет устройство в базе данных
     * @param deviceDTO - dto устройства
     * @param ipAddress - ip адрес
     * @throws DeviceException Ошибка! (устройство уже существует\неизвестная ошибка)
     */
    public void change(DeviceDTO deviceDTO, String ipAddress) throws DeviceException {
        DeviceEntity device = deviceRepository.getReferenceById(deviceDTO.getId());
        // Если у изменяемого объекта изменились поля тип и инвентарный номер, то необходимо выполнить проверку
        // на отсутствие новых значений этих полей в базе данных
        if (!deviceDTO.getInventoryNumber().equals(device.getInventoryNumber()) || !deviceDTO.getType().equals(device.getType())) {
            if (deviceRepository.existsByTypeAndInventoryNumber(deviceDTO.getType(), deviceDTO.getInventoryNumber())) {
                throw new DeviceException("Change error. Device already created.", device);
            }
        }
        try {
            deviceRepository.save(convert(deviceDTO));
        } catch (Exception e) {
            throw new DeviceException("Device change error.");
        }
    }

    /**
     * Удаляет устройство из базы данных
     * @param device - сущность устройство
     * @param ipAddress - ip адрес пользователя, который выполняет действие
     * @throws DeviceException  Ошибка! (неизвестная ошибка)
     */
    public void delete(DeviceEntity device, String ipAddress) throws DeviceException {
        try {
            deviceRepository.delete(device);
        } catch (Exception e) {
            throw new DeviceException("Device delete error.");
        }
    }

    /**
     * Метод конвертации DeviceDTO в DeviceEntity
     * @param deviceDTO - dto объект устройства
     * @return сущность DeviceEntity
     */
    public DeviceEntity convert(DeviceDTO deviceDTO) {
        String domainNameUsing = null;
        if (!ObjectUtils.isEmpty(deviceDTO.getUserUsing())) {
            domainNameUsing = deviceDTO.getUserUsing().getDomain();
        }
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
                .disposalDate(deviceDTO.getDisposalDate())
                .build();
    }

    /**
     * Метод конвертации DeviceEntity в DeviceDTO
     * @param device - dto объект устройства
     * @return сущность DeviceDTO
     */
    public DeviceDTO convert(DeviceEntity device) {
        UsersTelbookEntity user = null;
        if(!ObjectUtils.isEmpty(userTelbookService.getByDomain(device.getUserUsing()))) {
            user = userTelbookService.getByDomain(device.getUserUsing());
        }
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
                .userUsing(user)
                .disposalDate(device.getDisposalDate())
                .build();
    }
}
