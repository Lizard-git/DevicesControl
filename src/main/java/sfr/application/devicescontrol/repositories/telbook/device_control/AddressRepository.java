package sfr.application.devicescontrol.repositories.telbook.device_control;

import org.springframework.data.jpa.repository.JpaRepository;
import sfr.application.devicescontrol.entities.telbook.devices_control.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
