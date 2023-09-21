package sfr.application.devicescontrol.repositories.telbook.device_control;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceTypeEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.StatusEntity;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {
    Long countByStatus(StatusEntity status);
    Long countByStatusAndType(StatusEntity status, DeviceTypeEntity type);
    DeviceEntity findAllByTypeAndInventoryNumber(DeviceTypeEntity type, String inventoryNumber);
}
