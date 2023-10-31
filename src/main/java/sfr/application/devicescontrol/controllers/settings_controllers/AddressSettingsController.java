package sfr.application.devicescontrol.controllers.settings_controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sfr.application.devicescontrol.dto.AddressDto;
import sfr.application.devicescontrol.entities.telbook.devices_control.AddressEntity;
import sfr.application.devicescontrol.exceptions.AddressException;
import sfr.application.devicescontrol.services.AddressService;
import sfr.application.devicescontrol.services.SettlementsService;
import sfr.application.devicescontrol.utils.mapers.AddressConverter;

import java.net.UnknownHostException;

import static sfr.application.devicescontrol.utils.UtilsMethods.ipAddressValidator;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/settings/address")
public class AddressSettingsController {
    private final AddressService addressService;
    private final SettlementsService settlementsService;

    @GetMapping(value = {"", "/"})
    public String showPageAddress(
            @RequestParam(required = false, name = "successfully", defaultValue = "false") Boolean successfully,
            Model model
    ) {
        return showPage(model, "", new AddressDto(), successfully);
    }

    @GetMapping(value = {"/{id}"})
    public String showPageChangeAddress(@PathVariable(name = "id") AddressEntity address, Model model)
    {
        AddressConverter converter = new AddressConverter();
        model.addAttribute("Address", converter.convertToDto(address));
        return "settings/settings-change-address";
    }

    @PostMapping(value = {"/save"})
    public String NewAddress(
            HttpServletRequest request,
            @ModelAttribute("NewAddress") @Valid AddressDto addressDto,
            BindingResult bindingResult,
            Model model
    ) throws AddressException, UnknownHostException {
        if (bindingResult.hasErrors()) {
            return showPage(model, "", addressDto, false);
        }
        String ip = ipAddressValidator(request.getRemoteAddr());
        addressService.save(addressDto, ip);
        return "redirect:/settings/address?successfully=true";
    }

    @PostMapping(value = {"/change"})
    public String ChangeAddress(
            HttpServletRequest request,
            @ModelAttribute("Address") @Valid AddressDto addressDto,
            BindingResult bindingResult
    ) throws AddressException, UnknownHostException {
        if (bindingResult.hasErrors()) {
            return "settings/settings-change-address";
        }
        String ip = ipAddressValidator(request.getRemoteAddr());
        addressService.change(addressDto, ip);
        return "redirect:/settings/address?successfully=true";
    }

    @PostMapping(value = {"/delete/{id}"})
    public String DeleteAddress(
            HttpServletRequest request,
            @PathVariable(name = "id") AddressEntity address
    ) throws AddressException, UnknownHostException {
        String ip = ipAddressValidator(request.getRemoteAddr());
        addressService.delete(address, ip);
        return "redirect:/settings/address?successfully=true";
    }

    @RequestMapping(value = {"/error"})
    public String showPageErrors(@ModelAttribute("Error") String message, Model model) {
        return showPage(model, message, new AddressDto(), false);
    }

    @ModelAttribute
    public void ModelFilling(Model model) {
        model.addAttribute("AllSettlements", settlementsService.getAllSettlements());
        model.addAttribute("AllAddress", addressService.getAll());
    }

    private String showPage(Model model, String errorMessage, AddressDto addressDto, boolean successfully) {
        model.addAttribute("Error", errorMessage);
        model.addAttribute("NewAddress", addressDto);
        model.addAttribute("Successfully", successfully);
        return "settings/settings-address";
    }
}
