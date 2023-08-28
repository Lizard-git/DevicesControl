package sfr.application.devicescontrol.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sfr.application.devicescontrol.exceptions.AddressException;

import java.net.UnknownHostException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(AddressException.class)
    public String AddressException(AddressException e, RedirectAttributes redirectAttributes) {
        String message;
        switch (e.getMessage()) {
            case "Address save error" -> message = "Ошибка сохранения!";
            case "Address change error" -> message = "Ошибка изменения!";
            case "Error deleted" -> message = "Не удалось удалить адрес!";
            case "Address already exists" -> message = "Такой адрес уже существует!";
            case "Devices or users registered by address" -> message = "Ошибка удаления ! По адресу что то зарегистрировано!";
            default -> message = "Непредвиденная ошибка! Обратитесь к разработчикам!";
        }
        redirectAttributes.addFlashAttribute("Error", message);
        return "redirect:/settings/address/error";
    }

    @ExceptionHandler(UnknownHostException.class)
    public String UnknownHostException() {
        return "errors/error-ip";
    }

}
