package sfr.application.devicescontrol.repositories.telbook.device_control;

import org.springframework.data.jpa.repository.JpaRepository;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.HistoryDeviceEntity;

import java.util.List;

public interface HistoryDeviceRepository extends JpaRepository<HistoryDeviceEntity, Long> {

        List<HistoryDeviceEntity> findAllByDeviceOrderByDataHistoryDesc(DeviceEntity device);
}