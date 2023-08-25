package sfr.application.devicescontrol.entities.telbook.devices_control;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "devices", schema = "devices_control") //будешь менять не забуть поменять в ManyToMay связи
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "serial_Number")
    private String serialNumber;

    @Column(name = "inventory_Number", nullable = false, length = 100)
    private String inventoryNumber;

    @Column(name = "name_1C", length = 512)
    private String name;

    @Column(name = "cabinet")
    private String cabinet;

    @ManyToOne
    @JoinColumn(name = "id_Type", nullable = false)
    private DeviceTypeEntity type;

    //Костыль из-за недоступности первоначального источника
    //т.к. id в базе telbook меняются, то по хорошему надо заменить скрипт выгрузки в telbook из базы данных,
    //доступ к которой у меня закрыт, но telbook не моя база и менять я в ней ничего не буду (нужно разрешение)
    //так что используем из нее доменное имя в качестве "ID"
    @Column(name = "telbook_Domain_Name_User_Using")
    private String userUsing;

    @Column(name = "telbook_Domain_Name_User_Responsible")
    private String userResponsible; //Поле не указывается в программе но существует в бд (можно указать материально ответсвенного)
    //Костыль закончен

    @ManyToOne
    @JoinColumn(name = "id_Address", nullable = false)
    private AddressEntity address;

    @ManyToOne
    @JoinColumn(name = "id_Manufacturer")
    private ManufacturerEntity manufacturer;

    @Column(name = "MODEL")
    private String model;

    @ManyToOne
    @JoinColumn(name = "id_Status", nullable = false)
    private StatusEntity status;

    @Column(name =  "commissioning_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date commissioningDate;

    @Column(name = "warranty_date_with")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date warrantyDateWith;

    @Column(name = "warranty_date_by")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date warrantyDateBy;

    @JsonIgnore
    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<SpecificationsEntity> specifications;

    @JsonIgnore
    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<HistoryDeviceEntity> history;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "devices_consumables",
            joinColumns = {
                    @JoinColumn(
                            name = "id_device",
                            referencedColumnName = "id",
                            foreignKey = @ForeignKey(name = "FK0_Device"))
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "id_consumables",
                            referencedColumnName = "id",
                            foreignKey = @ForeignKey(name = "FK1_Consumables"))
            },
            schema = "devices_control"
    )
    private List<ConsumablesEntity> consumables;
}
