package sfr.application.devicescontrol.entities.telbook.devices_control;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

    @Size(max = 100, message = "Значение имени должно быть не более 50 символов")
    @NotEmpty(message = "Login не может быть пустым")
    @Column(name = "login", nullable = false, unique = true, length = 50)
    private String login;

    @Size()
    @NotEmpty(message = "Пароль не может быть пустым")
    @Column(name = "password", nullable = false)
    private String password;

    //Фамилия
    @Size(max = 255, message = "Значение фамилии должно быть не более 255 символов")
    @NotEmpty(message = "Фамилия не может быть пустым")
    @Column(name = "surname", nullable = false)
    private String surname;

    //Имя
    @Size(max = 255, message = "Значение имени должно быть не более 255 символов")
    @NotEmpty(message = "Имя не может быть пустым")
    @Column(name = "name", nullable = false)
    private String name;

    //Отчество
    @Size(max = 255, message = "Значение отчества должно быть не более 255 символов")
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
