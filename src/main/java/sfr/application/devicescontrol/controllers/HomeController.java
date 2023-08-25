package sfr.application.devicescontrol.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sfr.application.devicescontrol.entities.telbook.devices_control.StatusEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.UserEntity;
import sfr.application.devicescontrol.entities.telbook.prov_ter_org.UsersTelbookEntity;
import sfr.application.devicescontrol.enums.TypeEntity;
import sfr.application.devicescontrol.services.DeviceService;
import sfr.application.devicescontrol.services.StatusService;
import sfr.application.devicescontrol.services.UserService;
import sfr.application.devicescontrol.services.UserTelbookService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class HomeController {
    private final UserService userService;
    private final UserTelbookService userTelbookService;
    private final DeviceService deviceService;
    @GetMapping(value = {"", "/", "/home"})
    public String PageHome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserEntity user = userService.getUserByLoginNotDeleted(auth.getName());

        UsersTelbookEntity usersTelbook = userTelbookService.getByDomainAndTubNumber(user.getDomainName(), user.getTabNumber());
        List<StatusEntity> allStatusDevices = deviceService.getAllStatusByDevices();
        Map<String, Long> countByStatus = new HashMap<>();
        allStatusDevices.forEach(status -> countByStatus.put(status.getName(), deviceService.getCountByStatus(status)));

        model.addAttribute("AuthUser", user);
        model.addAttribute("AuthUserTelbook", usersTelbook);
        model.addAttribute("CountAllDevices", deviceService.getCountAll());
        model.addAttribute("CountDevicesByStatus", countByStatus);
        model.addAttribute("AllTypesDevice", deviceService.getAllTypesDevices());
        return "index";
    }
}
