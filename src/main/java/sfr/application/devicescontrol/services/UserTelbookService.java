package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.entities.telbook.prov_ter_org.UsersTelbookEntity;
import sfr.application.devicescontrol.repositories.telbook.prov_ter_org.UsersTelbookRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserTelbookService {
    private UsersTelbookRepository usersTelbookRepository;

    /**
     * Получает пользователя из телефонного справочника по его доменному имени и табельному номеру
     * @param domainName - доменное имя
     * @param tubNumber - таблельный номер
     * @return UsersTelbookEntity
     */
    public UsersTelbookEntity getByDomainAndTubNumber(String domainName, String tubNumber) {
        return usersTelbookRepository.findByDomainAndTubNumber(domainName, tubNumber);
    }

    /**
     * Вернет всех пользователей из телефонного справочника
     * @return List<UsersTelbookEntity>
     */
    public List<UsersTelbookEntity> getAll() {
        return usersTelbookRepository.findAll();
    }

    /**
     * Вернет пользователя из телефонного справочника по его доменному имени
     * @param domainName - доменное имя
     * @return UsersTelbookEntity
     */
    public UsersTelbookEntity getByDomain(String domainName) {
        return usersTelbookRepository.findByDomain(domainName);
    }
}
