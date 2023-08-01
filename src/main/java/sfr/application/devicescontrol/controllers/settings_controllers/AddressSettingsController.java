package sfr.application.devicescontrol.controllers.settings_controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sfr.application.devicescontrol.dto.AddressDto;
import sfr.application.devicescontrol.entities.telbook.devices_control.AddressEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.UserEntity;
import sfr.application.devicescontrol.exceptions.AddressException;
import sfr.application.devicescontrol.services.AddressService;
import sfr.application.devicescontrol.services.HistoryService;
import sfr.application.devicescontrol.services.UserService;

import java.net.UnknownHostException;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/settings/address")
public class AddressSettingsController {
    private final HistoryService historyService;
    private final AddressService addressService;
    private final UserService userService;
    @GetMapping(value = {"", "/"})
    public String PageAddress(
            @RequestParam(required = false, name = "successfully", defaultValue = "false") Boolean successfully,
            Model model) {
        model.addAttribute("Successfully", successfully);
        return "settings/settings-address";
    }

    @GetMapping(value = {"/{id}"})
    public String PageChangeAddress(@PathVariable(name = "id") AddressEntity address, Model model) {
        model.addAttribute("Address", addressService.convert(address));
        return "settings/settings-change-address";
    }

    @PostMapping(value = {"/save"})
    public String NewAddress(HttpServletRequest request,
                             @ModelAttribute("NewAddress") @Valid AddressDto addressDto,
                             BindingResult bindingResult) throws AddressException, UnknownHostException {
        String clientIp = request.getRemoteAddr();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUserByLoginNotDeleted(auth.getName());
        if (bindingResult.hasErrors()) {
            return "settings/settings-address";
        }
        addressService.saveAddress(addressDto);
        historyService.newHistoryInfo(
                "Добавил новый адрес в программу",
                clientIp,
                user
        );
        return "redirect:/settings/address?successfully=true";
    }

    @PostMapping(value = {"/change"})
    public String ChangeAddress(HttpServletRequest request,
                                @ModelAttribute("Address") @Valid AddressDto addressDto,
                                BindingResult bindingResult) throws AddressException, UnknownHostException {
        String clientIp = request.getRemoteAddr();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUserByLoginNotDeleted(auth.getName());
        if (bindingResult.hasErrors()) {
            return "settings/settings-address";
        }
        addressService.changeAddress(addressDto);
        historyService.newHistoryInfo(
                "Изменил адрес с ID: " + addressDto.getId(),
                clientIp,
                user
        );
        return "redirect:/settings/address?successfully=true";
    }

    @PostMapping(value = {"/delete/{id}"})
    public String DeleteAddress(HttpServletRequest request,
                                @PathVariable(name = "id") AddressEntity address, Model model) throws AddressException, UnknownHostException {
        String clientIp = request.getRemoteAddr();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUserByLoginNotDeleted(auth.getName());
        //Делаем все в руки пользователя. Если надо удалить то пускай переновсит всех пользователей и устройства с
        // этого адреса, а тут просто выведим ем что удаление невозможно т.к. по адресу что то находится
        if (address.getUsers().size() == 0) {// добавить проверку девайсов И расходников
            addressService.deleteAddress(address);
            historyService.newHistoryInfo(
                    "Успешное удаление адреса: " + address.getId() + ", " + address.getStreet() + ", " + address.getHouse(),
                    clientIp,
                    user
            );
        } else {
            historyService.newHistoryError(
                    "Пытался удалит адрес: " +
                            address.getId() + ", " +
                            address.getStreet() + ", " +
                            address.getHouse() +
                            " по которому в программе что то расположенно",
                    clientIp,
                    user
            );
            model.addAttribute("Error",
                    "Для удаления необходимо удалить/переместить все " +
                            "что находится по этому адресу (Пользователи, Устройства, Расходники)");
            return "settings/settings-address";
        }
        return "redirect:/settings/address?successfully=true";
    }

    @ExceptionHandler(AddressException.class)
    public String AddressException(AddressException e, Model model) {
        String messageUI;
        switch (e.getMessage()) {
            case "Address save error" -> messageUI = "Ошибка сохранения!";
            case "Address change error" -> messageUI = "Ошибка изменения!";
            case "Error deleted" -> messageUI = "Не удалось удалить адрес!";
            default -> messageUI = "Непредвиденная ошибка! Обратитесь к разработчикам!";
        }
        model.addAttribute("Successfully", false);
        model.addAttribute("NewAddress", new AddressDto());
        model.addAttribute("AllSettlements", addressService.getAllSettlements());
        model.addAttribute("AllAddress", addressService.getAllAddress());
        model.addAttribute("Error", messageUI);
        return "settings/settings-address";
    }

    @ExceptionHandler(UnknownHostException.class)
    public String UnknownHostException(Model model) {
        model.addAttribute("Successfully", false);
        model.addAttribute("NewAddress", new AddressDto());
        model.addAttribute("AllSettlements", addressService.getAllSettlements());
        model.addAttribute("AllAddress", addressService.getAllAddress());
        model.addAttribute("Error", "Ошибка ip адреса! Обратитесь к разработчикам!");
        return "settings/settings-address";
    }

    @ModelAttribute
    public void ModelFilling(Model model) {
        model.addAttribute("Error", "");
        model.addAttribute("AllSettlements", addressService.getAllSettlements());
        model.addAttribute("AllAddress", addressService.getAllAddress());
        model.addAttribute("NewAddress", new AddressDto());
    }
}
