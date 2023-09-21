package sfr.application.devicescontrol.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sfr.application.devicescontrol.exceptions.*;

import java.net.UnknownHostException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(AddressException.class)
    public String AddressException(AddressException e, RedirectAttributes redirectAttributes) {
        String message;
        switch (e.getMessage()) {
            case "Address save error." -> message = "Ошибка сохранения!";
            case "Address change error." -> message = "Ошибка изменения!";
            case "Address delete error." -> message = "Не удалось удалить адрес!";
            case "Address already exists." -> message = "Такой адрес уже существует!";
            case "Attempting to modify an entity that is not in the database." -> message = "Не удалось изменить адрес! такой адрес уже существует!";
            case "Failed to delete address. The entity is registered at the address." -> message = "Ошибка удаления ! По адресу что то зарегистрировано!";
            default -> message = "Непредвиденная ошибка! Обратитесь к разработчикам!";
        }
        redirectAttributes.addFlashAttribute("Error", message);
        return "redirect:/settings/address/error";
    }

    @ExceptionHandler(UsersExceptions.class)
    public String UserException(UsersExceptions e, RedirectAttributes redirectAttributes) {
        String message;
        switch (e.getMessage()) {
            case "Such user already exists" -> message = "Такой пользователь уже существует!";
            case "Failed to change user" -> message = "Не удалось изменить пользователя";
            default -> message = "Непредвиденная ошибка! Обратитесь к разработчикам";
        }
        redirectAttributes.addFlashAttribute("Error", message);
        return "redirect:/settings/users/error";
    }

    @ExceptionHandler(DeviceException.class)
    public String DeviceException(DeviceException e, RedirectAttributes redirectAttributes) {
        String message;
        switch (e.getMessage()) {
            case "Device save error" -> message = "Ошибка сохранения!";
            case "Device change error" -> message = "Ошибка изменения!";
            case "Device already created" -> message = "Ошибка ! такое устройство уже существует в программе !";
            case "Error deleted" -> message = "Не удалось удалить устройство!";
            default -> message = "Непредвиденная ошибка! Обратитесь к разработчикам!";
        }
        redirectAttributes.addFlashAttribute("Error", message);
        return "redirect:/devices/error";
    }

    @ExceptionHandler(DeviceTypeException.class)
    public String DeviceTypeException(DeviceTypeException e, RedirectAttributes redirectAttributes) {
        String message;
        switch (e.getMessage()) {
            case "Error. This type already exists" -> message = "Такой тип устройства уже существует!";
            case "Error." -> message = "Не удалось добавить тип!";
            default -> message = "Непредвиденная ошибка! Обратитесь к разработчикам";
        }
        redirectAttributes.addFlashAttribute("Error", message);
        return "redirect:/settings/devices/error";
    }

    @ExceptionHandler(DeviceManufacturerException.class)
    public String DeviceManufacturerException(DeviceManufacturerException e, RedirectAttributes redirectAttributes) {
        String message;
        switch (e.getMessage()) {
            case "Manufacturer already exists." -> message = "Такой производитель уже существует!";
            default -> message = "Непредвиденная ошибка! Обратитесь к разработчикам";
        }
        redirectAttributes.addFlashAttribute("Error", message);
        return "redirect:/settings/devices/error";
    }

    @ExceptionHandler(UnknownHostException.class)
    public String UnknownHostException() {
        return "errors/error-ip";
    }

    @ExceptionHandler(DataAccessException.class)
    public String DataBaseFailConnection() {
        return "errors/error-db-connect";
    }
}
