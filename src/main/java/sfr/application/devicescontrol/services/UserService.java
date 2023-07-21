package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.entities.telbook.devices_control.UserEntity;
import sfr.application.devicescontrol.repositories.telbook.device_control.UserRepository;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    /**
     * Вернет не удаленного пользователя по его login
     * @param login - login пользователя
     * @return UserEntity
     */
    public UserEntity getUserByLoginNotDeleted(String login) {
        return userRepository.findByLoginAndIsDeletedNull(login);
    }


}
