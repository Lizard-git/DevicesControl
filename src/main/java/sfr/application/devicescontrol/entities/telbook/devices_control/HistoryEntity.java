package sfr.application.devicescontrol.entities.telbook.devices_control;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "textСhange", nullable = false, length = 1000)
    private String textChange;

    @Column(name = "ipAddress", nullable = false)
    private String ipAddress;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "date", nullable = false)
    private Date dataHistory;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private UserEntity user;

    @Column(name = "dopInfo")
    private String dopInfo;
}
