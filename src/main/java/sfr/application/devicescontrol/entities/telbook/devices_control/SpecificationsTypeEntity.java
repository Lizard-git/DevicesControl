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
@Table(name = "specifications_type", schema = "devices_control")
public class SpecificationsTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_Device_Type", nullable = false)
    private DeviceTypeEntity type;

    @JsonIgnore
    @OneToMany(mappedBy = "type", cascade = CascadeType.REMOVE)
    private List<SpecificationsEntity> specifications;
}
