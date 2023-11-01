package sfr.application.devicescontrol.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sfr.application.devicescontrol.services.ExcelService;

import java.io.File;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/docs")
public class DocumentRestController {
    private final ExcelService excelService;

    @GetMapping(value = {"/unloading/full"})
    public ResponseEntity<FileSystemResource> fullUnloading() {
        // Путь к файлу, который нужно отдать
        String filePath = excelService.fullUnloading();
        File file = new File(filePath);

        // Установка заголовков ответа
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", file.getName());

        // Создание объекта FileSystemResource для файла
        FileSystemResource resource = new FileSystemResource(file);

        // Возвращение ResponseEntity с файлом и заголовками
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
