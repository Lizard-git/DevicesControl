package sfr.application.devicescontrol.repositories.telbook.device_control;

import org.springframework.data.jpa.repository.JpaRepository;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceTypeEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.SpecificationsTypeEntity;

import java.util.List;

public interface SpecificationsTypeRepository extends JpaRepository<SpecificationsTypeEntity, Long> {
    boolean existsByNameContainingIgnoreCaseAndType(String name, DeviceTypeEntity deviceType);

    List<SpecificationsTypeEntity> getAllByType(DeviceTypeEntity deviceType);

    SpecificationsTypeEntity getByDescriptionAndType(String description, DeviceTypeEntity type);
}