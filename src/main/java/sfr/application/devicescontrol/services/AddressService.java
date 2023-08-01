package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.dto.AddressDto;
import sfr.application.devicescontrol.entities.telbook.devices_control.AddressEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.SettlementsEntity;
import sfr.application.devicescontrol.exceptions.AddressException;
import sfr.application.devicescontrol.repositories.telbook.device_control.AddressRepository;
import sfr.application.devicescontrol.repositories.telbook.device_control.SettlementsRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AddressService {
    private final AddressRepository addressRepository;
    private final SettlementsRepository settlementsRepository;
    public List<AddressEntity> getAllAddress() {
        return addressRepository.findAll();
    }

    public List<SettlementsEntity> getAllSettlements() {return settlementsRepository.findAll();}

    public void saveAddress(AddressDto address) throws AddressException {
        try {
            addressRepository.save(convert(address));
        } catch (Exception e) {
            throw new AddressException("Address save error");
        }
    }

    public void changeAddress(AddressDto address) throws AddressException {
        try {
            addressRepository.save(convert(address));
        } catch (Exception e) {
            throw new AddressException("Address change error");
        }
    }

    public void deleteAddress(AddressEntity address) throws AddressException {
        try {
            addressRepository.delete(address);
        } catch (Exception e) {
            throw new AddressException("Error deleted");
        }
    }

    public AddressEntity convert(AddressDto addressDto) {
        return AddressEntity.builder()
                .id(addressDto.getId())
                .settlements(addressDto.getSettlements())
                .street(addressDto.getStreet())
                .house(addressDto.getHouse())
                .build();
    }

    public AddressDto convert(AddressEntity addressDto) {
        return AddressDto.builder()
                .id(addressDto.getId())
                .settlements(addressDto.getSettlements())
                .street(addressDto.getStreet())
                .house(addressDto.getHouse())
                .build();
    }
}
