package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.entities.telbook.devices_control.HistoryEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.UserEntity;
import sfr.application.devicescontrol.repositories.telbook.device_control.HistoryRepository;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Slf4j
public class HistoryService {
    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    private final HistoryRepository historyRepository;

    /**
     * Добавляет новую запись истории
     * @param change - изменения
     * @param ipAddress - ip адресс с которого производятся изменения
     * @param type - тип сообщения Error, Info, Warning
     * @param user - пользователь, который совершает действие
     * @param dopInfo - Дополнительная информация для записи
     * @throws UnknownHostException - Ошибка получения ip адреса
     */
    public void newHistory(String change, String ipAddress, String type, UserEntity user, String dopInfo) throws UnknownHostException {
        ipAddress = ipAddressValidator(ipAddress);
        historyRepository.save(HistoryEntity.builder()
                        .textChange(change)
                        .dataHistory(new Date())
                        .user(user)
                        .ipAddress(ipAddress)
                        .type(type)
                        .dopInfo(dopInfo)
                        .build());
    }
    /**
     * Добавляет новую запись истории
     * @param change - изменения
     * @param ipAddress - ip адресс с которого производятся изменения
     * @param type - тип сообщения Error, Info, Warning
     * @param user - пользователь, который совершает действие
     * @throws UnknownHostException - Ошибка получения ip адреса
     */
    public void newHistory(String change, String ipAddress, String type, UserEntity user) throws UnknownHostException {
        ipAddress = ipAddressValidator(ipAddress);
        historyRepository.save(HistoryEntity.builder()
                .textChange(change)
                .dataHistory(new Date())
                .user(user)
                .ipAddress(ipAddress)
                .type(type)
                .build());
    }
    /**
     * Добавляет новую запись истории типа Error
     * @param change - изменения
     * @param ipAddress - ip адресс с которого производятся изменения
     * @param user - пользователь, который совершает действие
     * @throws UnknownHostException - Ошибка получения ip адреса
     */
    public void newHistoryError(String change, String ipAddress, UserEntity user) throws UnknownHostException {
        ipAddress = ipAddressValidator(ipAddress);
        historyRepository.save(HistoryEntity.builder()
                .textChange(change)
                .dataHistory(new Date())
                .user(user)
                .ipAddress(ipAddress)
                .type("Error")
                .build());
    }

    /**
     * Добавляет новую запись истории типа Info
     * @param change - изменения
     * @param ipAddress - ip адресс с которого производятся изменения
     * @param user - пользователь, который совершает действие
     * @throws UnknownHostException - Ошибка получения ip адреса
     */
    public void newHistoryInfo(String change, String ipAddress, UserEntity user) throws UnknownHostException {
        ipAddress = ipAddressValidator(ipAddress);
        historyRepository.save(HistoryEntity.builder()
                .textChange(change)
                .dataHistory(new Date())
                .user(user)
                .ipAddress(ipAddress)
                .type("Info")
                .build());
    }

    /**
     * Добавляет новую запись истории типа Warning
     * @param change - изменения
     * @param ipAddress - ip адресс с которого производятся изменения
     * @param user - пользователь, который совершает действие
     * @throws UnknownHostException - Ошибка получения ip адреса
     */
    public void newHistoryWarning(String change, String ipAddress, UserEntity user) throws UnknownHostException {
        ipAddress = ipAddressValidator(ipAddress);
        historyRepository.save(HistoryEntity.builder()
                .textChange(change)
                .dataHistory(new Date())
                .user(user)
                .ipAddress(ipAddress)
                .type("Warning")
                .build());
    }

    /**
     * Валидация ip адреса
     * @param ipAddress - страка ip адреса
     * @return ip address
     * @throws UnknownHostException - ошибка получания ip адресса
     */
    public String ipAddressValidator(String ipAddress) throws UnknownHostException {
        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(ipAddress);
        if (matcher.matches()) {
            return ipAddress;
        }
        InetAddress address1 = InetAddress.getLocalHost();
        return address1.getHostAddress();
    }

    /**
     * Получает всю историяю из базы данных
     * @return List<HistoryEntity>
     */
    public List<HistoryEntity> getAllHistory() {
        return historyRepository.findAll();
    }

    /**
     * Получает всю историю из базы данных в виде объекта пагинации
     * @param pageable - пагинация
     * @return Page<HistoryEntity>
     */
    public Page<HistoryEntity> getAllHistory(Pageable pageable) {
        return historyRepository.findAll(pageable);
    }
}
