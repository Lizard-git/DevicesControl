package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.dto.SpecificationsTypeDto;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceTypeEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.SpecificationsTypeEntity;
import sfr.application.devicescontrol.exceptions.SpecificationsTypeException;
import sfr.application.devicescontrol.repositories.telbook.device_control.SpecificationsTypeRepository;
import sfr.application.devicescontrol.utils.mapers.SpecificationsTypeConverter;

import java.util.List;

import static sfr.application.devicescontrol.utils.UtilsMethods.russianToEnglishConverter;

@Service
@AllArgsConstructor
public class SpecificationsTypeService {

    private final SpecificationsTypeRepository specificationsTypeRepository;

    public List<SpecificationsTypeEntity> getAll() {
        return specificationsTypeRepository.findAll();
    }

    public List<SpecificationsTypeEntity> getAllByType(DeviceTypeEntity deviceType) {
        return specificationsTypeRepository.getAllByType(deviceType);
    }

    public SpecificationsTypeEntity getByDescriptionAndType(String description, DeviceTypeEntity type) {
        return specificationsTypeRepository.getByDescriptionAndType(description, type);
    }

    public void save(SpecificationsTypeDto specificationsTypeDto, String ipAddress) throws SpecificationsTypeException {
        if (specificationsTypeRepository.existsByNameContainingIgnoreCaseAndType(specificationsTypeDto.getName(), specificationsTypeDto.getType())) {
            throw new SpecificationsTypeException("Such a characteristic already exists for this type");
        }
        try {
            specificationsTypeDto.setDescription(russianToEnglishConverter(specificationsTypeDto.getName()));
            SpecificationsTypeConverter specificationsTypeConverter = new SpecificationsTypeConverter();
            specificationsTypeRepository.save(specificationsTypeConverter.convertToEntity(specificationsTypeDto));
        } catch (Exception e) {
            throw new SpecificationsTypeException("SpecificationsType save error");
        }
    }

    public void delete(SpecificationsTypeEntity specificationsType, String ipAddress) throws SpecificationsTypeException {
        try {
            specificationsTypeRepository.delete(specificationsType);
        } catch (Exception e) {
            throw new SpecificationsTypeException("SpecificationsType delete error");
        }
    }
}
