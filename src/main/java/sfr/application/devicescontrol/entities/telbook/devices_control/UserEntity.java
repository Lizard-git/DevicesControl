package sfr.application.devicescontrol.entities.telbook.devices_control;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "middle_Name")
    private String middleName;

    @ManyToOne
    @JoinColumn(name = "id_Role", nullable = false)
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "id_Address", nullable = false)
    private AddressEntity address;

    @Column(name = "is_Deleted")
    private Date isDeleted;

    @Column(name = "tab_Number", nullable = false, unique = true)
    private String tabNumber;

    @Column(name = "domain_Name", nullable = false, unique = true)
    private String domainName;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<HistoryEntity> history;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<HistoryDeviceEntity> historyDevice;
}
