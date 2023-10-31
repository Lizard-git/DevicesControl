package sfr.application.devicescontrol.entities.telbook.devices_control;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "specifications", schema = "devices_control")
public class SpecificationsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_Type", nullable = false)
    private SpecificationsTypeEntity type;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_Device", nullable = false)
    private DeviceEntity device;

    @Size(max = 100, message = "Слишком длинное значение!")
    @Column(name = "value", nullable = false)
    private String value;
}
