package sfr.application.devicescontrol.repositories.telbook.device_control;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sfr.application.devicescontrol.entities.telbook.devices_control.UserEntity;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /**
     * Получит пользователя по его login
     * @param login - login пользователя
     * @return UserEntity сущность пользователя
     */
    UserEntity findByLogin(String login);

    /**
     * Получит пользователя, если он не помечен как удаленный, по его login
     * @param login - login пользователя
     * @return UserEntity сущность пользователя
     */
    UserEntity findByLoginAndIsDeletedNull(String login);

    /**
     * Получает всех не удаленных пользователей
     * @return UserEntity
     */
    List<UserEntity> findAllByIsDeletedNull();

    UserEntity findByDomainName(String domainName);
}
