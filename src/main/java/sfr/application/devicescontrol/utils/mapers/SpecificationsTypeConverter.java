package sfr.application.devicescontrol.utils.mapers;

import org.modelmapper.ModelMapper;
import sfr.application.devicescontrol.dto.SpecificationsTypeDto;
import sfr.application.devicescontrol.entities.telbook.devices_control.SpecificationsTypeEntity;

public class SpecificationsTypeConverter {
    private final ModelMapper modelMapper;

    public SpecificationsTypeConverter() {
        this.modelMapper = new ModelMapper();
    }

    public SpecificationsTypeEntity convertToEntity(SpecificationsTypeDto specificationsDto) {
        return modelMapper.map(specificationsDto, SpecificationsTypeEntity.class);
    }

    public SpecificationsTypeConverter convertToDto(SpecificationsTypeEntity specificationsEntity) {
        return modelMapper.map(specificationsEntity, SpecificationsTypeConverter.class);
    }
}
