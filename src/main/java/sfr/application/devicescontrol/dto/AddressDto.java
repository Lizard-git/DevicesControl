package sfr.application.devicescontrol.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import sfr.application.devicescontrol.entities.telbook.devices_control.SettlementsEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {
    private Long id;

    private SettlementsEntity settlements;

    @Size(max = 100, message = "Название улицы")
    private String street;

    @Size(max = 100, message = "Значение имени должно быть не более 100 символов")
    @NotEmpty(message = "Номер здания не может быть пуст!")
    private String house;
}
