package sfr.application.devicescontrol.repositories.telbook.device_control;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceTypeEntity;

@Repository
public interface DeviceTypeRepository extends JpaRepository<DeviceTypeEntity, Long> {
}
