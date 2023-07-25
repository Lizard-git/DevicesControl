package sfr.application.devicescontrol.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import sfr.application.devicescontrol.entities.telbook.devices_control.RegionEntity;
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
    @NotEmpty(message = "Сокращенное имя не может быть пустым")
    private String street;

    @Size(max = 100, message = "Значение имени должно быть не менее 5 символов и не более 100")
    @NotEmpty(message = "Сокращенное имя не может быть пустым")
    private String house;

    // сокращенное имя для адреса

    private String name;

    // сам адрес
    @Size(min = 5, max = 255, message = "Значение адреса должно быть не менее 5 символов и не более 255")
    @NotEmpty(message = "Адрес не может быть пустым")
    private String address;

    // к какому региону относиться адрес
    @JsonIgnore
    private RegionEntity region;
}