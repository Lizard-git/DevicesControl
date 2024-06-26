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
    @JoinColumn(name = "id_Settlements", nullable = false)
    private SettlementsEntity settlements;

    @Column(name = "street")
    private String street;

    @Column(name = "house", nullable = false)
    private String house;

    @JsonIgnore
    @OneToMany(mappedBy = "address")
    private List<UserEntity> users;

    @JsonIgnore
    @OneToMany(mappedBy = "address")
    private List<DeviceEntity> devices;

    @JsonIgnore
    @OneToMany(mappedBy = "address")
    private List<ConsumablesEntity> consumables;

    @PreRemove
    private void clearAddressForUser() {
        users.forEach(userEntity ->
                userEntity.setAddress(null)
        );
        devices.forEach(device ->
                device.setAddress(null)
        );
        consumables.forEach(consumable ->
                consumable.setAddress(null));
    }
}
