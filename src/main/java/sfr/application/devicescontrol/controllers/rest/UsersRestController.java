package sfr.application.devicescontrol.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sfr.application.devicescontrol.entities.telbook.prov_ter_org.UsersTelbookEntity;
import sfr.application.devicescontrol.services.UserTelbookService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/users")
public class UsersRestController {
    private final UserTelbookService userTelbookService;

    @PostMapping(value = {"/get/{domain}"})
    public @ResponseBody UsersTelbookEntity GetUserByDomainName(@PathVariable String domain) {
        return userTelbookService.getByDomain(domain);
    }

    @PostMapping(value = {"/get/department/{department}"})
    public @ResponseBody List<UsersTelbookEntity> GetAllUsersByDepartment(@PathVariable String department) {
        return userTelbookService.getAllUserByDepartment(department);
    }

    @PostMapping(value = {"/get/{id}/department"})
    public @ResponseBody String GetDepartmentByUser(@PathVariable(name = "id") UsersTelbookEntity user) {
        return user.getDepartment();
    }
}
