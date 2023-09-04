package sfr.application.devicescontrol.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/info")
public class InfoController {
    @GetMapping(value = {"/home"})
    public String showInfoPage() {
        return "info/info-home";
    }
}
