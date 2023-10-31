package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.SpecificationsEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.SpecificationsTypeEntity;
import sfr.application.devicescontrol.enums.TypeMessagesHistory;
import sfr.application.devicescontrol.repositories.telbook.device_control.SpecificationsRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SpecificationsService {

    private final SpecificationsTypeService specificationsTypeService;
    private final SpecificationsRepository specificationsRepository;
    private final HistoryDevicesService historyDevicesService;
    public void save(Map<String, String> formData, DeviceEntity device) {
        Iterator<Map.Entry<String, String>> iterator = formData.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String value = entry.getValue();
            String key = entry.getKey();
            SpecificationsTypeEntity specificationsType = specificationsTypeService.getByDescriptionAndType(key, device.getType());

            if (specificationsRepository.existsByTypeAndDevice(specificationsType, device)) {
                SpecificationsEntity specifications = specificationsRepository.getByTypeAndDevice(specificationsType, device);
                specifications.setValue(value);
                specificationsRepository.save(specifications);
            } else {
                SpecificationsEntity specifications = SpecificationsEntity.builder()
                        .device(device)
                        .type(specificationsType)
                        .value(value)
                        .build();
                specificationsRepository.save(specifications);
            }
        }
        historyDevicesService.newHistory("Были внесены изменения в технические характеристики", device, TypeMessagesHistory.Info);
    }

    public List<SpecificationsEntity> getAllByDevice(DeviceEntity device) {
        return specificationsRepository.getAllByDevice(device);
    }
}
