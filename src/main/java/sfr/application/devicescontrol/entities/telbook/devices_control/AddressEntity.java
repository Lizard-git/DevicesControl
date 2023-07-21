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
@Table(name = "address", schema = "devices_control")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "city")
    private CityEntity city;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_region", nullable = false)
    private RegionEntity region;

    @Column(name = "street")
    private String street;

    @Column(name = "house")
    private String house;

    @Column(name = "section")
    private String section;

    @JsonIgnore
    @OneToMany(mappedBy = "address")
    private List<UserEntity> users;

//    @JsonIgnore
//    @OneToMany(mappedBy = "address")
//    private List<DevicesEntity> devices;
}
