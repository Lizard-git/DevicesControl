package sfr.application.devicescontrol.entities.telbook.devices_control;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users", schema = "devices_control")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login", nullable = false, unique = true, length = 100)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    //Фамилия
    @Column(name = "surname", nullable = false)
    private String surname;

    //Имя
    @Column(name = "name", nullable = false)
    private String name;

    //Отчество
    @Column(name = "middle_name")
    private String middleName;

    @ManyToOne
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "id_address", nullable = false)
    private AddressEntity address;

    @Column(name = "isDeleted")
    private Date isDeleted;

    @Column(name = "tabNumber", nullable = false)
    private String tabNumber;

    @Column(name = "domainName", nullable = false)
    private String domainName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<HistoryEntity> history;
}
