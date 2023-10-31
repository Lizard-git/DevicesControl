package sfr.application.devicescontrol.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceTypeEntity;

/**
 * DTO for {@link sfr.application.devicescontrol.entities.telbook.devices_control.SpecificationsTypeEntity}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecificationsTypeDto {
    private Long id;

    @NotEmpty(message = "Обязательное поле!")
    private String name;

    private String description;

    @NotNull(message = "Обязательное поле!")
    private DeviceTypeEntity type;
}