package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sfr.application.devicescontrol.configs.properties.AddressMessagesProperties;
import sfr.application.devicescontrol.dto.AddressDto;
import sfr.application.devicescontrol.entities.telbook.devices_control.AddressEntity;
import sfr.application.devicescontrol.exceptions.AddressException;
import sfr.application.devicescontrol.repositories.telbook.device_control.AddressRepository;
import sfr.application.devicescontrol.utils.mapers.AddressConverter;

import java.net.UnknownHostException;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@EnableConfigurationProperties(AddressMessagesProperties.class)
public class AddressService {

    private final AddressRepository addressRepository;
    private final HistoryService historyService;

    @Autowired
    private AddressMessagesProperties addressMessagesProperties;

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
     * @throws AddressException - Ошибка при сохранении адреса (такой адрес уже существует\неизвестная ошибка).
     * @throws UnknownHostException - Ошибка получения ip адреса.
     */
    public void save(AddressDto address, String ipAddress) throws AddressException, UnknownHostException {
        //Проверяем объект на наличие id. В методе save должен отсутствовать.
        if (!ObjectUtils.isEmpty(address.getId())) {
            historyService.newHistoryWarning(addressMessagesProperties.getAddressObjectHasIdMessage(), ipAddress);
            throw  new AddressException("Address already exists");
        }

            // Смотрим наличие сохраняемого адреса в бд
            AddressEntity checkAddress = addressRepository.findBySettlementsAndStreetAndHouse(
                    address.getSettlements(),
                    address.getStreet(),
                    address.getHouse()
            );
            if (ObjectUtils.isEmpty(checkAddress)) {
                //если в бд нет то сохраняем
                AddressConverter converter = new AddressConverter();
                try {
                    addressRepository.save(converter.convertToEntity(address));
                } catch (Exception e) {
                    historyService.newHistoryError(addressMessagesProperties.getErrorSaveAddressMessage(), ipAddress);
                    throw new AddressException("Address save error");
                }
                historyService.newHistoryInfo(addressMessagesProperties.getSuccessAddNewAddressMessage(), ipAddress);
            } else {
                historyService.newHistoryWarning(addressMessagesProperties.getAddressAlreadyExistsMessage(), ipAddress);
                throw new AddressException("Address already exists");
            }
    }

    /**
     * Метод для изменения адреса
     * @param address - изменяемый адрес
     * @param ipAddress - ip адрес пользователя, который сохраняет.
     * @throws AddressException - Ошибка при сохранении адреса (у изменяемого объекта ид равен нулл\неизвестная ошибка).
     * @throws UnknownHostException - Ошибка получения ip адреса.
     */
    public void change(AddressDto address, String ipAddress) throws AddressException, UnknownHostException {
        //Проверяем объект на наличие id. В методе change должен присутствовать.
        if (ObjectUtils.isEmpty(address.getId())) {
            historyService.newHistoryWarning(addressMessagesProperties.getWarningMutableObjectIdIsNullMessage(), ipAddress);
            throw  new AddressException("Mutable object id is null");
        }
        AddressConverter converter = new AddressConverter();
        AddressEntity changedAddress = converter.convertToEntity(address);
        AddressEntity checkAddress = addressRepository.getReferenceById(address.getId());
        if (!checkAddress.equals(changedAddress)) {
            try {
                addressRepository.save(changedAddress);
            } catch (Exception e) {
                historyService.newHistoryError(addressMessagesProperties.getErrorChangeAddressMessage(), ipAddress);
                throw new AddressException("Address change error");
            }
            historyService.newHistoryInfo(addressMessagesProperties.getSuccessChangeAddressMessage(), ipAddress);
        }

    }

    public void delete(AddressEntity address, String ipAddress) throws AddressException, UnknownHostException {
        if (address.getUsers().isEmpty() && address.getConsumables().isEmpty() && address.getDevices().isEmpty()) {
            String addressStr = address.getSettlements().getRegion().getName() + " " +
                    address.getSettlements().getName() + " " +
                    address.getStreet() + " " +
                    address.getHouse();
            try {
                addressRepository.delete(address);
            } catch (Exception e) {
                historyService.newHistoryError(addressMessagesProperties.getErrorDeleteAddressMessage(), ipAddress);
                throw new AddressException("Error deleted");
            }
            historyService.newHistoryInfo(addressMessagesProperties.getSuccessDeletedAddressMessage() + addressStr, ipAddress);
        } else {
            historyService.newHistoryError(addressMessagesProperties.getErrorObjectAreLocatedAddressEntityMessage(), ipAddress);
            throw new AddressException("Devices or users registered by address");
        }
    }
}
