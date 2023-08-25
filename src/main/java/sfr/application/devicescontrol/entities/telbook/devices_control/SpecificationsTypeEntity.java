package sfr.application.devicescontrol.entities.telbook.devices_control;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "specifications_type", schema = "devices_control")
public class SpecificationsTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_Device_Type", nullable = false)
    private DeviceTypeEntity type;
}
