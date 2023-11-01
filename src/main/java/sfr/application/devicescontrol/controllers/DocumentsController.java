package sfr.application.devicescontrol.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sfr.application.devicescontrol.services.ExcelService;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/doc")
public class DocumentsController {
    private final ExcelService excelService;
    @GetMapping(value = {"/home"})
    public String PageAllDevice() {
        return "documents/documents-home";
    }

//    @RequestMapping(value = {"files/{name}"}, method = RequestMethod.GET)
//    public void getFile(@PathVariable("name") String fileName, HttpServletResponse response) {
//        Path path = Paths.get("temp" + "/" + fileName);
//        if (Files.exists(path)){
//            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
//            response.setContentType("application/vnd.ms-excel");
//            try {
//                Files.copy(path, response.getOutputStream());
//                response.getOutputStream().flush();
//            } catch (IOException e) {
//                throw new RuntimeException("IOError writing file to output stream");
//            }
//        }
//    }
}
