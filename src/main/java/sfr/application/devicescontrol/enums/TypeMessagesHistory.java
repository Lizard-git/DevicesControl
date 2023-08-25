package sfr.application.devicescontrol.enums;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum TypeMessagesHistory {
    Info ("Информирование"),
    Warning  ("Предупреждение"),
    Error ("Ошибка");
//    All("Все");

    private String title;

    TypeMessagesHistory(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
