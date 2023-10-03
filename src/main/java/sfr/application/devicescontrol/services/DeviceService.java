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
import sfr.application.devicescontrol.enums.TypeMessagesHistory;
import sfr.application.devicescontrol.exceptions.DeviceException;
import sfr.application.devicescontrol.repositories.telbook.device_control.DeviceRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static sfr.application.devicescontrol.utils.UtilsMethods.*;

@Service
@AllArgsConstructor
@Slf4j
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final UserTelbookService userTelbookService;
    private final HistoryDevicesService historyDevicesService;

    private static final String defaultMessage = "В устройстве изменено(ы) поле(я): ";

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
        return new TreeMap<>(result);
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
            DeviceEntity device = deviceRepository.save(convert(deviceDTO));
            historyDevicesService.newHistory("Устройство добавлено в систему", device, TypeMessagesHistory.Info);
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
            DeviceEntity deviceConvert = convert(deviceDTO);
            if (!device.equals(deviceConvert)) {
                String testChanges = getMessageIdentifyingChanges(deviceConvert, device);
                device = deviceRepository.save(deviceConvert);
                historyDevicesService.newHistory(testChanges, device, TypeMessagesHistory.Info);
            } else {
                throw new DeviceException("The device has not changed.", device);
            }
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

    private static String getCheckChangesInStringParam(String changesParam, String paramDefault) {
        if (changesParam.isEmpty()) { return " c: " + paramDefault + " на: пустое значение"; }
        if (paramDefault.isEmpty()) { return " c: пустого значения на: " + changesParam; }
        return " c: " + paramDefault + " на: " + changesParam;
    }

    private static void checkAndAppendChanges(String paramName, String changeValue, String defaultValue, StringBuilder message) {
        if (!ObjectUtils.nullSafeEquals(changeValue, defaultValue)) {
            if (!message.toString().equals(DeviceService.defaultMessage)) {
                message.append("; ");
            }
            message.append(paramName).append(getCheckChangesInStringParam(changeValue, defaultValue));
        }
    }

    private static String getMessageIdentifyingChanges(DeviceEntity deviceChange, DeviceEntity deviceDefault) {
        StringBuilder message = new StringBuilder(defaultMessage);
        checkAndAppendChanges("Инвентарный номер",
                deviceChange.getInventoryNumber(),
                deviceDefault.getInventoryNumber(),
                message
        );
        checkAndAppendChanges("Адрес",
                formatAddress(deviceChange.getAddress()),
                formatAddress(deviceDefault.getAddress()),
                message
        );

        checkAndAppendChanges("Производитель",
                formatManufacturer(deviceChange.getManufacturer()),
                formatManufacturer(deviceDefault.getManufacturer()),
                message
        );
        checkAndAppendChanges("Модель",
                deviceChange.getModel(),
                deviceDefault.getModel(),
                message
        );
        checkAndAppendChanges("Имя 1С",
                deviceChange.getName(),
                deviceDefault.getName(),
                message
        );
        checkAndAppendChanges("Дата ввода в эксплуатацию",
                formatDate(deviceChange.getCommissioningDate()),
                formatDate(deviceDefault.getCommissioningDate()),
                message
        );
        checkAndAppendChanges("Серийный номер",
                deviceChange.getSerialNumber(),
                deviceDefault.getSerialNumber(),
                message
        );
        checkAndAppendChanges("Кабинет",
                deviceChange.getCabinet(),
                deviceDefault.getCabinet(),
                message
        );
        checkAndAppendChanges("Статус",
                formatStatus(deviceChange.getStatus()),
                formatStatus(deviceDefault.getStatus()),
                message
        );
        checkAndAppendChanges("Пользователя устройством",
                formatUserUsing(deviceChange.getUserUsing()),
                formatUserUsing(deviceDefault.getUserUsing()),
                message
        );
        checkAndAppendChanges("Дата гарантии \"С\"",
                formatDate(deviceChange.getWarrantyDateWith()),
                formatDate(deviceDefault.getWarrantyDateWith()),
                message
        );
        checkAndAppendChanges("Дата гарантии \"По\"",
                formatDate(deviceChange.getWarrantyDateBy()),
                formatDate(deviceDefault.getWarrantyDateBy()),
                message
        );
        checkAndAppendChanges("Дата списания",
                formatDate(deviceChange.getDisposalDate()),
                formatDate(deviceDefault.getDisposalDate()),
                message
        );
        if (message.toString().equals(defaultMessage)) {
            return "Изменений не было !";
        } else {
            message.append(".");
        }
        return message.toString();
    }
}
