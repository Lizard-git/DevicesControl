package sfr.application.devicescontrol.repositories.telbook.device_control;

import org.springframework.data.jpa.repository.JpaRepository;
import sfr.application.devicescontrol.entities.telbook.devices_control.SpecificationsEntity;

public interface SpecificationsRepository extends JpaRepository<SpecificationsEntity, Long> {
}