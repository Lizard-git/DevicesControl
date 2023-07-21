package sfr.application.devicescontrol.entities.telbook.devices_control;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "regions", schema = "devices_control")
public class RegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Код района
    @Size(max = 5, message = "Значение кода региона должно быть не более 5 символов")
    @NotEmpty(message = "Код региона не может быть пустым")
    @Column(name = "code", nullable = false, unique = true, length = 5)
    private String code;

    // Имя района
    @Size(max = 3000, message = "Значение имени должно быть не менее 5 символов и не более 3000")
    @NotEmpty(message = "Имя региона не может быть пустым")
    @Column(name = "name", nullable = false, length = 3000)
    private String name;

    //Город
    @ManyToOne
    @JoinColumn(name = "city")
    private CityEntity city;

    // Адреса района
    @OneToMany(mappedBy = "region")
    private List<AddressEntity> address;
}
