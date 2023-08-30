package sfr.application.devicescontrol.utils.mapers;

import org.modelmapper.ModelMapper;
import sfr.application.devicescontrol.dto.AddressDto;
import sfr.application.devicescontrol.entities.telbook.devices_control.AddressEntity;

public class AddressConverter {
    private final ModelMapper modelMapper;

    public AddressConverter() {
        this.modelMapper = new ModelMapper();
    }

    public AddressEntity convertToEntity(AddressDto addressDto) {
        return modelMapper.map(addressDto, AddressEntity.class);
    }

    public AddressDto convertToDto(AddressEntity addressEntity) {
        return modelMapper.map(addressEntity, AddressDto.class);
    }
}
