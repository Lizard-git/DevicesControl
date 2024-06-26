package sfr.application.devicescontrol.entities.telbook.devices_control;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "roles", schema = "devices_control")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Имя роли (В базе задавать с префиксом "ROLE_")
    @Column(name = "name" , nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "description", nullable = false, unique = true, length = 50)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<UserEntity> user;
}
