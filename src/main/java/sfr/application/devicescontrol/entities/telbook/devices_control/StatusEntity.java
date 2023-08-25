package sfr.application.devicescontrol.entities.telbook.devices_control;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import sfr.application.devicescontrol.enums.TypeEntity;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "status", schema = "devices_control")
public class StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeEntity type;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "status")
    private List<DeviceEntity> devices;

//    @JsonIgnore
//    @OneToMany(mappedBy = "status")
//    private List<ConsumablesEntity> consumables;
}
