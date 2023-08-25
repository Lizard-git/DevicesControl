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
@Table(name = "consumables_types", schema = "devices_control")
public class ConsumablesTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_Device_Type", nullable = false)
    private DeviceTypeEntity deviceType;

    @JsonIgnore
    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private List<ConsumablesEntity> consumables;
}
