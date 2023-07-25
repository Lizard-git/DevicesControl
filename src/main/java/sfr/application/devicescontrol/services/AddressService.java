package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.entities.telbook.devices_control.AddressEntity;
import sfr.application.devicescontrol.repositories.telbook.device_control.AddressRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AddressService {
    private final AddressRepository addressRepository;

    public List<AddressEntity> getAll() {
        return addressRepository.findAll();
    }
}
