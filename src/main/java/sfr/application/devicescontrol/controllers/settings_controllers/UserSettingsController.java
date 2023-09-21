package sfr.application.devicescontrol.controllers.settings_controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import sfr.application.devicescontrol.dto.UserDto;
import sfr.application.devicescontrol.entities.telbook.devices_control.UserEntity;
import sfr.application.devicescontrol.exceptions.UsersExceptions;
import sfr.application.devicescontrol.services.AddressService;
import sfr.application.devicescontrol.services.RoleService;
import sfr.application.devicescontrol.services.UserService;
import sfr.application.devicescontrol.services.UserTelbookService;
import sfr.application.devicescontrol.utils.UtilsMethods;

import java.net.UnknownHostException;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/settings/users")
public class UserSettingsController {
    private final UserTelbookService userTelbookService;
    private final AddressService addressService;
    private final RoleService roleService;
    private final UserService userService;

    @GetMapping(value = {"", "/"})
    public String showPageSettingsUser(
            @RequestParam(required = false, name = "successfully", defaultValue = "false") Boolean successfully,
            Model model
    ) {
        return showPage(model, "", successfully);
    }

    @GetMapping(value = {"/{id}"})
    public String showPageUser(
            @PathVariable(name = "id") UserEntity user,
            Model model
    ) {
        model.addAttribute("User", userService.convert(user));
        return "settings/settings-change-users";
    }

    @PostMapping (value = {"/create"})
    public String createUser(
            HttpServletRequest request,
            @ModelAttribute("NewUser") @Valid UserDto userDto,
            BindingResult bindingResult,
            Model model
    ) throws UsersExceptions, UnknownHostException {
        if(ObjectUtils.isEmpty(userDto.getPassword()) || userDto.getPassword().length() < 6) {
            bindingResult.addError(
                    new FieldError("NewUser",
                            "password",
                            "Пароль не может быть пуст и должен быть более 6 символов")
            );
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("Error", "");
            return "settings/settings-user";
        }
        String ip = UtilsMethods.ipAddressValidator(request.getRemoteAddr());
        userService.save(userDto, ip);
        return "redirect:/settings/users?successfully=true";
    }

    @PostMapping (value = {"/change"})
    public String changeUser(
            HttpServletRequest request,
            @ModelAttribute("User") @Valid UserDto userDto,
            BindingResult bindingResult,
            Model model
    ) throws UsersExceptions, UnknownHostException {
        if(userDto.getPassword().length() < 6 && !userDto.getPassword().isEmpty()) {
           bindingResult.addError(
                   new FieldError("NewUser",
                           "password",
                           "Пароль должен быть более 6 символов")
           );
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("Error", "");
            return "settings/settings-user";
        }
        String ip = UtilsMethods.ipAddressValidator(request.getRemoteAddr());
        userService.change(userDto, ip);
        return "redirect:/settings/users?successfully=true";
    }

    @PostMapping(value = {"/remove/{id}"})
    public String removeUser(
            HttpServletRequest request,
            @PathVariable(name = "id") UserEntity user
    ) throws UsersExceptions, UnknownHostException {
        String ip = UtilsMethods.ipAddressValidator(request.getRemoteAddr());
        userService.remove(user, ip);
        return "redirect:/settings/users?successfully=true";
    }

    @PostMapping(value = {"/delete/{id}"})
    public String deleteUser(
            HttpServletRequest request,
            @PathVariable(name = "id") UserEntity user
    ) throws UsersExceptions, UnknownHostException {
        String ip = UtilsMethods.ipAddressValidator(request.getRemoteAddr());
        userService.delete(user, ip);
        return "redirect:/settings/users?successfully=true";
    }

    @RequestMapping(value = {"/error"})
    public String showPageErrors(@ModelAttribute("Error") String message, Model model) {
        return showPage(model, message, false);
    }

    private String showPage(Model model, String errorMessage, boolean successfully) {
        model.addAttribute("Error", errorMessage);
        model.addAttribute("Successfully", successfully);
        return "settings/settings-user";
    }

    @ModelAttribute
    public void ModelFilling(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUserByLoginNotDeleted(auth.getName());
        model.addAttribute("User", userService.convert(user));
        model.addAttribute("AllAddress", addressService.getAll());
        model.addAttribute("AllRoles", roleService.getAll());
        model.addAttribute("AllUsersTelbook", userTelbookService.getAll());
        model.addAttribute("AllUsers", userService.getAll());
        model.addAttribute("NewUser", new UserDto());
    }
}
