package sfr.application.devicescontrol.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegionDto {
    private Long id;
    // Код района
    @Size(max = 6, message = "Значение кода региона должно быть не более 6 символов")
    @NotEmpty(message = "Код региона не может быть пустым")
    private String code;

    // Имя района
    @Size(max = 1000, message = "Значение имени должно быть не более 1000")
    @NotEmpty(message = "Имя региона не может быть пустым")
    private String name;

    @Column(name = "street")
    private String street;

    @Column(name = "house")
    private String house;
}
