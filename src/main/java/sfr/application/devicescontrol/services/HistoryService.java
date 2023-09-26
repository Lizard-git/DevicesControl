package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.entities.telbook.devices_control.HistoryEntity;
import sfr.application.devicescontrol.enums.TypeMessagesHistory;
import sfr.application.devicescontrol.repositories.telbook.device_control.HistoryRepository;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final SecurityService securityService;
    /**
     * Получает всю историю из базы данных
     * @return List<HistoryEntity>
     */
    public List<HistoryEntity> getAll() {
        return historyRepository.findAll();
    }

    /**
     * Получает всю историю из базы данных в виде объекта пагинации
     * @param pageable - пагинация
     * @return Page<HistoryEntity>
     */
    public Page<HistoryEntity> getAll(Pageable pageable) {
        return historyRepository.findAll(pageable);
    }

    /**
     * Добавляет новую запись истории
     * @param change - изменения
     * @param ipAddress - ip адрес с которого производятся изменения
     * @param type - тип сообщения Error, Info, Warning
     * @param dopInfo - Дополнительная информация для записи
     */
    public void newHistory(String change, String ipAddress, TypeMessagesHistory type, String dopInfo) {
        historyRepository.save(HistoryEntity.builder()
                        .textChange(change)
                        .dataHistory(new Date())
                        .user(securityService.getCurrentUser())
                        .ipAddress(ipAddress)
                        .type(type)
                        .dopInfo(dopInfo)
                        .build());
    }

    /**
     * Добавляет новую запись истории
     * @param change - изменения
     * @param ipAddress - ip адрес с которого производятся изменения
     * @param type - тип сообщения Error, Info, Warning
     */
    public void newHistory(String change, String ipAddress, TypeMessagesHistory type) {
        historyRepository.save(HistoryEntity.builder()
                .textChange(change)
                .dataHistory(new Date())
                .user(securityService.getCurrentUser())
                .ipAddress(ipAddress)
                .type(type)
                .build());
    }

    /**
     * Добавляет новую запись истории типа Error
     * @param change - изменения
     * @param ipAddress - ip адрес с которого производятся изменения
     */
    public void newHistoryError(String change, String ipAddress) {
        historyRepository.save(HistoryEntity.builder()
                .textChange(change)
                .dataHistory(new Date())
                .user(securityService.getCurrentUser())
                .ipAddress(ipAddress)
                .type(TypeMessagesHistory.Error)
                .build());
    }

    /**
     * Добавляет новую запись истории типа Info
     * @param change - изменения
     * @param ipAddress - ip адрес с которого производятся изменения
     */
    public void newHistoryInfo(String change, String ipAddress) {
        historyRepository.save(HistoryEntity.builder()
                .textChange(change)
                .dataHistory(new Date())
                .user(securityService.getCurrentUser())
                .ipAddress(ipAddress)
                .type(TypeMessagesHistory.Info)
                .build());
    }

    /**
     * Добавляет новую запись истории типа Warning
     * @param change - изменения
     * @param ipAddress - ip адрес с которого производятся изменения
     */
    public void newHistoryWarning(String change, String ipAddress) {
        historyRepository.save(HistoryEntity.builder()
                .textChange(change)
                .dataHistory(new Date())
                .user(securityService.getCurrentUser())
                .ipAddress(ipAddress)
                .type(TypeMessagesHistory.Warning)
                .build());
    }
}
