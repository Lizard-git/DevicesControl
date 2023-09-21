package sfr.application.devicescontrol.repositories.telbook.device_control;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sfr.application.devicescontrol.entities.telbook.devices_control.AddressEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.SettlementsEntity;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    boolean existsBySettlementsAndStreetAndHouse(SettlementsEntity settlements, String street, String house);
}
