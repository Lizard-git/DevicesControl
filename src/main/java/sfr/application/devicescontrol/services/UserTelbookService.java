package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.entities.telbook.prov_ter_org.UsersTelbookEntity;
import sfr.application.devicescontrol.repositories.telbook.prov_ter_org.UsersTelbookRepository;

@Service
@AllArgsConstructor
@Slf4j
public class UserTelbookService {
    private UsersTelbookRepository usersTelbookRepository;

    public UsersTelbookEntity getByDomainAndTubNumber(String domainName, String tubNumber) {
        return usersTelbookRepository.findByDomainAndTubNumber(domainName, tubNumber);
    }
}
