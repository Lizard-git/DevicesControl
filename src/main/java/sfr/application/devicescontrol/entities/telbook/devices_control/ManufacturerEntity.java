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
@Table(name = "manufacturer", schema = "devices_control")
public class ManufacturerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeEntity typeColumn;

    @JsonIgnore
    @OneToMany(mappedBy = "manufacturer")
    private List<DeviceEntity> devices;

    // лист расходников
}
