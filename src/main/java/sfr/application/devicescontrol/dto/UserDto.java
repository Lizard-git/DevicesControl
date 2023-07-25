package sfr.application.devicescontrol.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import sfr.application.devicescontrol.entities.telbook.devices_control.AddressEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.RoleEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;

    @Size(max = 100, message = "Login должно быть не более 100 символов")
    @NotEmpty(message = "Login не может быть пустым")
    private String login;

    private String password;

    //Фамилия
    @Size(max = 255, message = "Значение фамилии должно быть не более 255 символов")
    @NotEmpty(message = "Фамилия не может быть пустым")
    private String surname;

    //Имя
    @Size(max = 255, message = "Значение имени должно быть не более 255 символов")
    @NotEmpty(message = "Имя не может быть пустым")
    private String name;

    //Отчество
    @Size(max = 255, message = "Значение имени должно быть не более 255 символов")
    private String middleName;

    private RoleEntity role;

    private AddressEntity address;
}
