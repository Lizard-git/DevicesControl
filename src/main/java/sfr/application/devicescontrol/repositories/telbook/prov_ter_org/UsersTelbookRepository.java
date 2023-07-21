package sfr.application.devicescontrol.repositories.telbook.prov_ter_org;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sfr.application.devicescontrol.entities.telbook.prov_ter_org.UsersTelbookEntity;

import java.util.List;

public interface UsersTelbookRepository extends JpaRepository<UsersTelbookEntity, Long> {
    @Query(value = "select PROV_TER_ORG.USERS.DEPARTAMENT from PROV_TER_ORG.USERS group by PROV_TER_ORG.USERS.DEPARTAMENT", nativeQuery = true)
    List<String> getAllDepartment();

    UsersTelbookEntity findByDomainAndTubNumber(String domain, String tubNumber);

    UsersTelbookEntity findByDomain(String domain);
}
