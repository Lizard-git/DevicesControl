package sfr.application.devicescontrol.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import sfr.application.devicescontrol.entities.telbook.devices_control.AddressEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceTypeEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.ManufacturerEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.StatusEntity;
import sfr.application.devicescontrol.entities.telbook.prov_ter_org.UsersTelbookEntity;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceDTO {

    private Long id;

    @Size(max = 100, message = "Серийный номер более чем 255 символов невозможен")
    private String serialNumber;

    @Size(max = 100, message = "Инвентарный более чем 100 символов невозможен")
    @NotEmpty(message = "Обязательное поле!")
    private String inventoryNumber;

    @Size(max = 512, message = "Не такое длинное... уж простите...")
    private String name;

    @Size(max = 10, message = "Что-то длинный номер кабинета у вас!")
    private String cabinet;

    @NotNull(message = "Обязательное поле!")
    private DeviceTypeEntity type;

    private UsersTelbookEntity userUsing;

    private UsersTelbookEntity userResponsible;

    @NotNull(message = "Обязательное поле!")
    private AddressEntity address;

    private ManufacturerEntity manufacturer;

    private String model;

    @NotNull(message = "Обязательное поле!")
    private StatusEntity status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date commissioningDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date warrantyDateWith;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date warrantyDateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date disposalDate;
}
