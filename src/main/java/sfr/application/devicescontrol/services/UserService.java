package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sfr.application.devicescontrol.dto.UserDto;
import sfr.application.devicescontrol.entities.telbook.devices_control.UserEntity;
import sfr.application.devicescontrol.exceptions.UsersExceptions;
import sfr.application.devicescontrol.repositories.telbook.device_control.UserRepository;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserTelbookService userTelbookService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Вернет не удаленного пользователя по его login
     * @param login - login пользователя
     * @return UserEntity
     */
    public UserEntity getUserByLoginNotDeleted(String login) {
        return userRepository.findByLoginAndIsDeletedNull(login);
    }

    /**
     * Вернет всех пользователей программы
     * @return List<UserEntity>
     */
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    /**
     * Создает нового пользователя
     * @param userDto - пользовательские данные
     * @throws UsersExceptions - ошибка сохранения, скорее всего такой пользователь уже существует
     */
    public void save(UserDto userDto) throws UsersExceptions {
        try {
            userRepository.save(convert(userDto));
        } catch (Exception e) {
            throw new UsersExceptions("Such user already exists");
        }
    }

    /**
     * Изменяет пользователя
     * @param userDto - новые пользовательские данные
     * @throws UsersExceptions - ошибка изменения
     */
    public void change(UserDto userDto) throws UsersExceptions {
        try {
            UserEntity user = userRepository.getReferenceById(userDto.getId());
            userRepository.save(merger(userDto, user));
        } catch (Exception e) {
            throw new UsersExceptions("Failed to change user");
        }
    }

    /**
     * Удаление пользователя (пометкой о удалении)
     * @param user - удаляемый пользователь
     * @throws UsersExceptions - ошибка удаления
     */
    public void remove(UserEntity user) throws UsersExceptions {
        try {
            user.setIsDeleted(new Date());
            userRepository.save(user);
        } catch (Exception e) {
            throw new UsersExceptions("Failed to remove user");
        }
    }

    /**
     * Удаление пользователя навсегда
     * @param user - удаляемый пользователь
     * @throws UsersExceptions - ошибка удаления
     */
    public void delete(UserEntity user) throws UsersExceptions {
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            throw new UsersExceptions("Failed to delete user");
        }
    }

    /**
     * Преобразует UserDto в UserEntity, если это возможно.
     * @param userDto - пользовательские данные
     * @return UserEntity
     * @throws UsersExceptions - ошибка если конвертация невозможна
     */
    public UserEntity convert(UserDto userDto) throws UsersExceptions {
        if (ObjectUtils.isEmpty(userTelbookService.getByDomain(userDto.getLogin()))) {
            throw new UsersExceptions("Failed to convert DTO to Entity");
        }
        if (ObjectUtils.isEmpty(userDto.getPassword())) {
            throw new UsersExceptions("Error when converting DTO to Entity. Password is empty.");
        }
        return UserEntity.builder()
                .id(userDto.getId())
                .login(userDto.getLogin())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .address(userDto.getAddress())
                .role(userDto.getRole())
                .surname(userDto.getSurname())
                .name(userDto.getName())
                .middleName(userDto.getMiddleName())
                .domainName(userTelbookService.getByDomain(userDto.getLogin()).getDomain())
                .tabNumber(userTelbookService.getByDomain(userDto.getLogin()).getTubNumber())
                .build();
    }

    /**
     * Преобразует UserEntity в UserDto
     * @param userEntity - сущность пользователя
     * @return UserDto
     */
    public UserDto convert(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .login(userEntity.getLogin())
                .role(userEntity.getRole())
                .address(userEntity.getAddress())
                .surname(userEntity.getSurname())
                .name(userEntity.getName())
                .middleName(userEntity.getMiddleName())
                .build();
    }

    /**
     * Слияние объектов UserDto и UserEntity.
     * @param userDto - данные пользователя
     * @param userEntity - сущность пользователя
     * @return UserEntity
     */
    public UserEntity merger(UserDto userDto, UserEntity userEntity) {
        String password;
        if (userDto.getPassword().isEmpty()) {
            password = userEntity.getPassword();
        } else {
            password = passwordEncoder.encode(userDto.getPassword());
        }
        userEntity.setLogin(userDto.getLogin());
        userEntity.setPassword(password);
        userEntity.setAddress(userDto.getAddress());
        userEntity.setRole(userDto.getRole());
        userEntity.setName(userDto.getName());
        userEntity.setSurname(userDto.getSurname());
        userEntity.setMiddleName(userDto.getMiddleName());
        return userEntity;
    }
}
