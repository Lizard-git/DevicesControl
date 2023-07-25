package sfr.application.devicescontrol.controllers.settings_controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/settings/consumable")
public class ConsumableSettingsController {
    @GetMapping(value = {"","/"})
    public String PageConsumable() {
        return "settings/settings-consumable";
    }
}
