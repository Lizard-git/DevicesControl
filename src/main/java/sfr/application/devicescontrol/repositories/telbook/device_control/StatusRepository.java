package sfr.application.devicescontrol.repositories.telbook.device_control;

import org.springframework.data.jpa.repository.JpaRepository;
import sfr.application.devicescontrol.entities.telbook.devices_control.StatusEntity;
import sfr.application.devicescontrol.enums.TypeEntity;

import java.util.List;

public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
    StatusEntity getByNameAndType(String name, TypeEntity type);
    List<StatusEntity> getAllByType(TypeEntity type);
}
