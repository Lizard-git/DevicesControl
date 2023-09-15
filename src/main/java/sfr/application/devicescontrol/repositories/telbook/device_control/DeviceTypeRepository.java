package sfr.application.devicescontrol.repositories.telbook.device_control;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceTypeEntity;

import java.util.List;

@Repository
public interface DeviceTypeRepository extends JpaRepository<DeviceTypeEntity, Long> {
//    @Query(nativeQuery = true, value = "SELECT NAME FROM DEVICES_CONTROL.DEVICES_TYPES WHERE LOWER(NAME) LIKE LOWER(concat(concat('%',:name),'%'))")
//    List<String> getAllName(String name);

    List<DeviceTypeEntity> findAllByNameContaining(String name);
}
