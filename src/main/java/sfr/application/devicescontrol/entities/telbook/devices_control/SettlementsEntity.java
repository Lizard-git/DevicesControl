package sfr.application.devicescontrol.entities.telbook.devices_control;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "settlements", schema = "devices_control")
public class SettlementsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "id_region", nullable = false)
    private RegionEntity region;

    @OneToMany(mappedBy = "settlements", cascade = CascadeType.REMOVE)
    private List<AddressEntity> address;
}
