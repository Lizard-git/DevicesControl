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
import sfr.application.devicescontrol.services.*;

import java.net.UnknownHostException;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/settings/users")
public class UserSettingsController {
    private final HistoryService historyService;
    private final UserTelbookService userTelbookService;
    private final AddressService addressService;
    private final RoleService roleService;
    private final UserService userService;

    @GetMapping(value = {"", "/"})
    public String PageUser(@RequestParam(required = false, name = "successfully", defaultValue = "false") Boolean successfully,
                           Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUserByLoginNotDeleted(auth.getName());
        model.addAttribute("User", userService.convert(user));
        model.addAttribute("Successfully", successfully);
        return "settings/settings-user";
    }

    @GetMapping(value = {"/{id}"})
    public String GetUser(@PathVariable(name = "id") UserEntity user, Model model) {
        model.addAttribute("User", userService.convert(user));
        return "settings/settings-change-users";
    }

    @PostMapping (value = {"/create"})
    public String NewUser(HttpServletRequest request,
                          @ModelAttribute("NewUser") @Valid UserDto userDto,
                          BindingResult bindingResult,
                          Model model) throws UsersExceptions, UnknownHostException {
        String clientIp = request.getRemoteAddr();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUserByLoginNotDeleted(auth.getName());
        if(ObjectUtils.isEmpty(userDto.getPassword()) || userDto.getPassword().length() < 6) {
            bindingResult.addError(
                    new FieldError("NewUser",
                            "password",
                            "Пароль не может быть пуст и должен быть более 6 символов")
            );
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("User", userService.convert(user));
            return "settings/settings-user";
        }
        userService.save(userDto);
        historyService.newHistoryInfo(
                "Добавил нового пользователя",
                clientIp
        );
        return "redirect:/settings/users?successfully=true";
    }

    @PostMapping (value = {"/change"})
    public String ChangeUser(HttpServletRequest request,
                             @ModelAttribute("User") @Valid UserDto userDto,
                             BindingResult bindingResult) throws UsersExceptions, UnknownHostException {
        String clientIp = request.getRemoteAddr();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUserByLoginNotDeleted(auth.getName());
        if(userDto.getPassword().length() < 6 && !userDto.getPassword().isEmpty()) {
           bindingResult.addError(
                   new FieldError("NewUser",
                           "password",
                           "Пароль должен быть более 6 символов")
           );
        }
        if (bindingResult.hasErrors()) {
            return "settings/settings-user";
        }
        userService.change(userDto);
        historyService.newHistoryInfo(
                "Успешно изменил данные пользователя: " + userDto.getLogin(),
                clientIp
        );
        return "redirect:/settings/users?successfully=true";
    }

    @PostMapping(value = {"/remove/{id}"})
    public String RemoveUser(HttpServletRequest request,
                             @PathVariable(name = "id") UserEntity user) throws UsersExceptions, UnknownHostException {
        String clientIp = request.getRemoteAddr();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity authUser = userService.getUserByLoginNotDeleted(auth.getName());
        userService.remove(user);
        historyService.newHistoryInfo(
                "Успешно пометил пользователя " + user.getLogin() + " как: 'Удаленный'",
                clientIp
        );
        return "redirect:/settings/users?successfully=true";
    }

    @PostMapping(value = {"/delete/{id}"})
    public String DeleteUser(HttpServletRequest request,
                             @PathVariable(name = "id") UserEntity user) throws UsersExceptions, UnknownHostException {
        String clientIp = request.getRemoteAddr();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity authUser = userService.getUserByLoginNotDeleted(auth.getName());
        userService.delete(user);
        historyService.newHistoryInfo(
                "Успешно полностью удалил пользователя " + user.getLogin() + " из системы",
                clientIp
        );
        return "redirect:/settings/users?successfully=true";
    }

    @ExceptionHandler(UsersExceptions.class)
    public String UserException(UsersExceptions e, Model model) {
        String messageUI;
        switch (e.getMessage()) {
            case "Such user already exists" -> messageUI = "Такой пользователь уже существует!";
            case "Failed to change user" -> messageUI = "Не удалось изменить пользователя";
            default -> messageUI = "Непредвиденная ошибка! Обратитесь к разработчикам";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUserByLoginNotDeleted(auth.getName());
        model.addAttribute("User", userService.convert(user));
        model.addAttribute("Error", messageUI);
        model.addAttribute("NewUser", new UserDto());
        model.addAttribute("AllUsersTelbook", userTelbookService.getAll());
        model.addAttribute("AllAddress", addressService.getAll());
        model.addAttribute("AllRoles", roleService.getAll());
        model.addAttribute("AllUsers", userService.getAll());
        return "settings/settings-user";
    }

    @ExceptionHandler(UnknownHostException.class)
    public String UnknownHostException(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUserByLoginNotDeleted(auth.getName());
        model.addAttribute("User", userService.convert(user));
        model.addAttribute("Error", "Ошибка ip адреса! Обратитесь к разработчикам!");
        model.addAttribute("NewUser", new UserDto());
        model.addAttribute("AllUsersTelbook", userTelbookService.getAll());
        model.addAttribute("AllAddress", addressService.getAll());
        model.addAttribute("AllRoles", roleService.getAll());
        model.addAttribute("AllUsers", userService.getAll());
        return "settings/settings-user";
    }

    @ModelAttribute
    public void ModelFilling(Model model) {
        model.addAttribute("NewUser", new UserDto());
        model.addAttribute("Error", "");
        model.addAttribute("AllUsersTelbook", userTelbookService.getAll());
        model.addAttribute("AllAddress", addressService.getAll());
        model.addAttribute("AllRoles", roleService.getAll());
        model.addAttribute("AllUsers", userService.getAll());
    }
}
