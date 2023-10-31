package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.entities.telbook.devices_control.ManufacturerEntity;
import sfr.application.devicescontrol.enums.TypeEntity;
import sfr.application.devicescontrol.exceptions.DeviceManufacturerException;
import sfr.application.devicescontrol.repositories.telbook.device_control.ManufacturerRepository;

import java.util.List;

import static java.util.Comparator.comparing;

@Service
@AllArgsConstructor
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    /**
     * Вернет список всех производителей устройств из базы дынных
     * @return List<ManufacturerEntity>
     */
    public List<ManufacturerEntity> getAll() {
        List<ManufacturerEntity> result = manufacturerRepository.findAll();
        result.sort(comparing(ManufacturerEntity::getName));
        return result;
    }

    /**
     * Метод для создания и сохранения в базе данных нового производителя устройств
     * @param name - наименование производителя
     * @param ipAddress - ip адрес
     * @throws DeviceManufacturerException - Ошибка сохранения (такой производитель уже существует\неизвестная ошибка)
     */
    public void save(String name, String ipAddress) throws DeviceManufacturerException{
        if (manufacturerRepository.existsByNameContainingIgnoreCase(name)) {
            throw new DeviceManufacturerException("Manufacturer already exists.");
        }
        try {
            manufacturerRepository.save(ManufacturerEntity.builder().name(name).typeColumn(TypeEntity.device).build());
        } catch (Exception e) {
            throw new DeviceManufacturerException("Manufacturer save error.");
        }
    }

    /**
     * Удаляет производителя из базы данных
     * @param manufacturerEntity - сущность производителя
     * @throws DeviceManufacturerException - Ошибка сохранения (неизвестная ошибка)
     */
    public void delete(ManufacturerEntity manufacturerEntity) throws DeviceManufacturerException {
        try {
            manufacturerRepository.delete(manufacturerEntity);
        } catch (Exception e) {
            throw new DeviceManufacturerException("Manufacturer delete error.");
        }
    }
}
