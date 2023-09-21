package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import sfr.application.devicescontrol.configs.properties.AddressMessagesProperties;
import sfr.application.devicescontrol.dto.AddressDto;
import sfr.application.devicescontrol.entities.telbook.devices_control.AddressEntity;
import sfr.application.devicescontrol.exceptions.AddressException;
import sfr.application.devicescontrol.repositories.telbook.device_control.AddressRepository;
import sfr.application.devicescontrol.utils.mapers.AddressConverter;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@EnableConfigurationProperties(AddressMessagesProperties.class)
public class AddressService {

    private final AddressRepository addressRepository;

    /**
     * Получает список адресов зарегистрированных в программе
     * @return List<AddressEntity>
     */
    public List<AddressEntity> getAll() {
        return addressRepository.findAll();
    }

    /**
     * Метод для сохранения нового адреса в программе.
     * @param address - сохраняемы адрес.
     * @param ipAddress - ip адрес пользователя, который сохраняет.
     * @throws AddressException - Ошибка при сохранении адреса (такой адрес уже существует или неизвестная ошибка).
     */
    public void save(AddressDto address, String ipAddress) throws AddressException {
        if (addressRepository.existsBySettlementsAndStreetAndHouse(
                address.getSettlements(),
                address.getStreet(),
                address.getHouse())
        ) { throw new AddressException("Address already exists."); }

        try {
            AddressConverter converter = new AddressConverter();
            addressRepository.save(converter.convertToEntity(address));
        } catch (Exception e) { throw new AddressException("Address save error."); }
    }

    /**
     * Метод для изменения адреса
     * @param address - изменяемый адрес
     * @param ipAddress - ip адрес пользователя, который сохраняет.
     * @throws AddressException - Ошибка при изменении адреса (Попытка изменить не существующий адрес или
     * неизвестная ошибка).
     */
    public void change(AddressDto address, String ipAddress) throws AddressException {
        if (!addressRepository.existsById(address.getId())) {
            throw new AddressException("Attempting to modify an entity that is not in the database.");
        }
        if (addressRepository.existsBySettlementsAndStreetAndHouse(
                address.getSettlements(),
                address.getStreet(),
                address.getHouse())
        ) { throw new AddressException("Address already exists."); }
        try {
            AddressConverter converter = new AddressConverter();
            addressRepository.save(converter.convertToEntity(address));
        } catch (Exception e) {
            throw new AddressException("Address change error.");
        }
    }

    /**
     * Метод удаляет сущность адреса из базы данных в случае если по адресу нет зарегистрированных сущностей
     * @param address - сущность адреса
     * @param ipAddress - IP адрес
     * @throws AddressException - Ошибка при удалении (по адресу может быть что-то зарегистрировано или неизвестная ошибка)
     */
    public void delete(AddressEntity address, String ipAddress) throws AddressException {
        if (!address.getUsers().isEmpty() || !address.getConsumables().isEmpty() || !address.getDevices().isEmpty()) {
            throw new AddressException("Failed to delete address. The entity is registered at the address.");
        }
        try {
            addressRepository.delete(address);
        } catch (Exception e) { throw new AddressException("Address delete error.");}
    }
}
