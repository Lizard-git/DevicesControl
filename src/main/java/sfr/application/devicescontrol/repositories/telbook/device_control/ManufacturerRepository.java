package sfr.application.devicescontrol.repositories.telbook.device_control;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sfr.application.devicescontrol.entities.telbook.devices_control.ManufacturerEntity;
import sfr.application.devicescontrol.enums.TypeEntity;

import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository<ManufacturerEntity, Long> {
    List<ManufacturerEntity> findAllByTypeColumn(TypeEntity type);
}
