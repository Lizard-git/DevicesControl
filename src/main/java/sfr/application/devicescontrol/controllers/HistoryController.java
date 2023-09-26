package sfr.application.devicescontrol.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sfr.application.devicescontrol.services.HistoryService;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/history")
public class HistoryController {
    private final HistoryService historyService;
    @GetMapping(value = {"/home", "/home/{page}"})
    public String PageAllDevice(@PathVariable(name = "page", required = false) Integer page, Model model) {
        if (ObjectUtils.isEmpty(page)) page = 0;
        model.addAttribute("AllHistory",
                historyService.getAll(
                        PageRequest.of(page, 100, Sort.by("id").descending())
                )
        );
        return "history/history-home";
    }
}
