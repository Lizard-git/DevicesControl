package sfr.application.devicescontrol.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/doc")
public class DocumentsController {
    @GetMapping(value = {"/home"})
    public String PageAllDevice() {
        return "documents/documents-home";
    }
}
