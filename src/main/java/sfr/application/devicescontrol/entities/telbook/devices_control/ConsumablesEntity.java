package sfr.application.devicescontrol.entities.telbook.devices_control;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "consumables", schema = "devices_control")
public class ConsumablesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_Manufacturer")
    private ManufacturerEntity manufacturer;

    @Column(name = "model")
    private String model;

    @ManyToOne
    @JoinColumn(name = "id_Address", nullable = false)
    private AddressEntity address;

    @ManyToOne
    @JoinColumn(name = "id_Consumables_Type", nullable = false)
    private ConsumablesTypeEntity type;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "price_For_One")
    private Integer priceForOne;

    @ManyToOne
    @JoinColumn(name = "id_Status", nullable = false)
    private StatusEntity status;

    @Column(name = "specifications", length = 2000)
    private String specifications;

    @JsonIgnore
    @ManyToMany(mappedBy = "consumables", fetch = FetchType.LAZY)
    private List<DeviceEntity> devices;
}
