package sfr.application.devicescontrol.entities.telbook.devices_control;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import sfr.application.devicescontrol.enums.TypeMessagesHistory;

import java.util.Date;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "history_devices", schema = "devices_control")
public class HistoryDeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text_Сhange", nullable = false, length = 1000)
    private String textChange;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeMessagesHistory type;

    @Column(name = "date", nullable = false)
    private Date dataHistory;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_Device", nullable = false)
    private DeviceEntity device;

    @Column(name = "dop_Info")
    private String dopInfo;
}
