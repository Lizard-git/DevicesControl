package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceEntity;
import sfr.application.devicescontrol.entities.telbook.prov_ter_org.UsersTelbookEntity;

import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ExcelService {
    private final DeviceService deviceService;
    private final UserTelbookService userTelbookService;
    @SneakyThrows
    public String fullUnloading() {
        // создание самого excel файла в памяти
        XSSFWorkbook workbook = new XSSFWorkbook();
        // создание листа с названием "Просто лист"
        XSSFSheet sheet = workbook.createSheet("Full Unloading");

        // счетчик для строк
        int rowNum = 0;

        // создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("Уникальный номер в программе");
        row.createCell(1).setCellValue("Тип устройства");
        row.createCell(2).setCellValue("Инвентарный номер");
        row.createCell(3).setCellValue("Серийный номер");
        row.createCell(4).setCellValue("Производитель");
        row.createCell(5).setCellValue("Модель");
        row.createCell(6).setCellValue("Статус");
        row.createCell(7).setCellValue("Расположение");
        row.createCell(8).setCellValue("Отдел");
        row.createCell(9).setCellValue("Пользователь");

        List<DeviceEntity> devices = deviceService.getAll();
        for (DeviceEntity device : devices){
            rowNum += 1;
            row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(device.getId().toString());
            row.createCell(1).setCellValue(device.getType().getName());
            row.createCell(2).setCellValue("'" + device.getInventoryNumber());
            row.createCell(3).setCellValue("'" + device.getSerialNumber());
            row.createCell(4).setCellValue(ObjectUtils.isEmpty(device.getManufacturer()) ? "" : device.getManufacturer().getName());
            row.createCell(5).setCellValue("'" + device.getModel());
            row.createCell(6).setCellValue(device.getStatus().getName());
            row.createCell(7).setCellValue(ObjectUtils.isEmpty(device.getAddress()) ? "" :
                    ("Район: " + device.getAddress().getSettlements().getRegion().getName()) + " " +
                            device.getAddress().getSettlements().getType() + ": " +
                            device.getAddress().getSettlements().getName() + " " +
                            device.getAddress().getStreet() + " " +
                            device.getAddress().getHouse()
                    );

            if (ObjectUtils.isEmpty(device.getUserUsing())) {
                row.createCell(8).setCellValue("");
                row.createCell(9).setCellValue("");
            } else {
                UsersTelbookEntity usersTelbook = userTelbookService.getByDomain(device.getUserUsing());
                String department = ObjectUtils.isEmpty(usersTelbook) ? "" : usersTelbook.getDepartment();
                String name = ObjectUtils.isEmpty(usersTelbook) ? "" : usersTelbook.getName();
                row.createCell(8).setCellValue(department);
                row.createCell(9).setCellValue(name);
            }
        }
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        sheet.autoSizeColumn(8);
        sheet.autoSizeColumn(9);
        String fileName = "temp/FullUploading " + UUID.randomUUID() + ".xlsx";
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();
        return fileName;
    }
}
