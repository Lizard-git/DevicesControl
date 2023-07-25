package sfr.application.devicescontrol.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sfr.application.devicescontrol.entities.telbook.prov_ter_org.UsersTelbookEntity;
import sfr.application.devicescontrol.services.UserTelbookService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/users")
public class UsersRestController {
    private final UserTelbookService userTelbookService;

    @PostMapping(value = {"/get/{domain}"})
    public @ResponseBody UsersTelbookEntity GetUserByDomainName(@PathVariable String domain) {
        return userTelbookService.getByDomain(domain);
    }
}
