package sfr.application.devicescontrol.entities.telbook.devices_control;

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
@Table(name = "history", schema = "devices_control")
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text_Сhange", nullable = false, length = 1000)
    private String textChange;

    @Column(name = "ip_Address", nullable = false)
    private String ipAddress;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeMessagesHistory type;

    @Column(name = "date", nullable = false)
    private Date dataHistory;

    @ManyToOne
    @JoinColumn(name = "id_User", nullable = false)
    private UserEntity user;

    @Column(name = "dop_Info")
    private String dopInfo;
}
