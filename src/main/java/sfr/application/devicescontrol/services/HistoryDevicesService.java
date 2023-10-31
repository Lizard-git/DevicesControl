package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.HistoryDeviceEntity;
import sfr.application.devicescontrol.enums.TypeMessagesHistory;
import sfr.application.devicescontrol.repositories.telbook.device_control.HistoryDeviceRepository;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class HistoryDevicesService {
    private final HistoryDeviceRepository historyDeviceRepository;
    private final SecurityService securityService;
    /**
     * Получает всю историю всех устройств из базы данных
     * @return List<HistoryDeviceEntity>
     */
    public List<HistoryDeviceEntity> getAll() {
        return historyDeviceRepository.findAll();
    }

    /**
     * Получает всю историю всех устройств из базы данных в виде объекта пагинации
     * @param pageable - пагинация
     * @return Page<HistoryEntity>
     */
    public Page<HistoryDeviceEntity> getAll(Pageable pageable) {
        return historyDeviceRepository.findAll(pageable);
    }

    /**
     * Получает историю конкретного устройства
     * @param device - устройство, историю которого необходимо узнать
     * @return List<HistoryDeviceEntity>
     */
    public List<HistoryDeviceEntity> getAllByDevice(DeviceEntity device) {
        return historyDeviceRepository.findAllByDeviceOrderByDataHistoryDesc(device);
    }

    /**
     * Добавляет новую запись истории устройства
     * @param change - изменение
     * @param device - устройство, над которым производится изменение
     * @param type - тип сообщения Error, Info, Warning
     * @param dopInfo - Дополнительная информация для записи
     */
    public void newHistory(String change, DeviceEntity device, TypeMessagesHistory type, String dopInfo) {
        historyDeviceRepository.save(HistoryDeviceEntity.builder()
                .textChange(change)
                .device(device)
                .dataHistory(new Date())
                .user(securityService.getCurrentUser())
                .type(type)
                .dopInfo(dopInfo)
                .build());
    }

    /**
     * Добавляет новую запись истории устройства
     * @param change - изменение
     * @param device - устройство, над которым производится изменение
     * @param type - тип сообщения Error, Info, Warning
     */
    public void newHistory(String change, DeviceEntity device, TypeMessagesHistory type) {
        historyDeviceRepository.save(HistoryDeviceEntity.builder()
                .textChange(change)
                .device(device)
                .dataHistory(new Date())
                .user(securityService.getCurrentUser())
                .type(type)
                .build());
    }
}
