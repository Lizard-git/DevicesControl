package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.entities.telbook.devices_control.RoleEntity;
import sfr.application.devicescontrol.repositories.telbook.device_control.RoleRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RoleService {
    private final RoleRepository roleRepository;

    /**
     * Получает все роли из базы данных
     * @return List<RoleEntity>
     */
    public List<RoleEntity> getAll() {
        return roleRepository.findAll();
    }
}
