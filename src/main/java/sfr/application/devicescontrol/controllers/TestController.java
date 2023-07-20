package sfr.application.devicescontrol.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class TestController {
    @GetMapping(value = "")
    public String PageHome() {
        return "index";
    }
    @GetMapping(value = "/plogin")
    public String PageLogin() {
        return "login";
    }
}
