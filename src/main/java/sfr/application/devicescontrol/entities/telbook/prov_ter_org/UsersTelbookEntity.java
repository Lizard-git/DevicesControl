package sfr.application.devicescontrol.entities.telbook.prov_ter_org;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Subselect("select " +
        "PROV_TER_ORG.USERS.ID id, " +
        "PROV_TER_ORG.USERS.FIO name, " +
        "PROV_TER_ORG.USERS.TAB_NUM tubNumber, " +
        "PROV_TER_ORG.USERS.DEPARTAMENT department, " +
        "PROV_TER_ORG.USERS.FUNCT_USER position, " +
        "PROV_TER_ORG.USERS.NAME_DOMAIN domain, " +
        "PROV_TER_ORG.USERS.DATE_BIRTH birthday " +
        "from PROV_TER_ORG.USERS")
@Synchronize({"PROV_TER_ORG.USERS"})
public class UsersTelbookEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "department")
    private String department;

    @Column(name = "position")
    private String position;

    @Column(name = "tubNumber")
    private String tubNumber;

    @Column(name = "domain")
    private String domain;

    @Column(name = "birthday")
    private Date birthday;
}
