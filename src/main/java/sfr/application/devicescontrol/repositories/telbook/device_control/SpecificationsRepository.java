package sfr.application.devicescontrol.repositories.telbook.device_control;

import org.springframework.data.jpa.repository.JpaRepository;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.SpecificationsEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.SpecificationsTypeEntity;

import java.util.List;

public interface SpecificationsRepository extends JpaRepository<SpecificationsEntity, Long> {
    boolean existsByTypeAndDevice(SpecificationsTypeEntity type, DeviceEntity device);

    SpecificationsEntity getByTypeAndDevice(SpecificationsTypeEntity type, DeviceEntity device);

    List<SpecificationsEntity> getAllByDevice(DeviceEntity device);
}