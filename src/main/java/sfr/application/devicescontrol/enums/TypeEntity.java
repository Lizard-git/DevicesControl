package sfr.application.devicescontrol.enums;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum TypeEntity {
    device ("Устройства"),
    consumable ("Расходники");
//    All("Все");

    private String title;

    TypeEntity(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
