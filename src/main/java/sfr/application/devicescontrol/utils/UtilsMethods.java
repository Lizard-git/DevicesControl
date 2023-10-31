package sfr.application.devicescontrol.utils;

import sfr.application.devicescontrol.entities.telbook.devices_control.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.nonNull;

public class UtilsMethods {
    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    /**
     * Валидация ip адреса
     * @param ipAddress - страка ip адреса
     * @return ip address
     * @throws UnknownHostException - ошибка получания ip адресса
     */
    public static String ipAddressValidator(String ipAddress) throws UnknownHostException {
        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(ipAddress);
        if (matcher.matches()) {
            return ipAddress;
        }
        InetAddress address1 = InetAddress.getLocalHost();
        return address1.getHostAddress();
    }

    public static String russianToEnglishConverter(String input) {
        // Создаем массивы с русскими и английскими символами
        String[] russianLetters = {"а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э", "ю", "я"};
        String[] englishLetters = {"a", "b", "v", "g", "d", "e", "yo", "zh", "z", "i", "y", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f", "h", "ts", "ch", "sh", "shch", "", "y", "", "e", "yu", "ya"};

        // Проходимся по каждому символу входной строки
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            String currentChar = String.valueOf(input.charAt(i));
            boolean isRussian = false;

            // Проверяем, является ли текущий символ русским
            for (int j = 0; j < russianLetters.length; j++) {
                if (currentChar.equalsIgnoreCase(russianLetters[j])) {
                    // Если символ русский, заменяем его на английский эквивалент
                    output.append(englishLetters[j]);
                    isRussian = true;
                    break;
                }
            }

            // Если символ не является русским, оставляем его без изменений
            if (!isRussian) {
                output.append(currentChar);
            }
        }

        return output.toString();
    }

    public static Map<SpecificationsTypeEntity, String> mergeListSpecification(
            List<SpecificationsTypeEntity> types,
            List<SpecificationsEntity> specifications
    ) {
        Map<SpecificationsTypeEntity, String> resultMap = new HashMap<>();
        for (SpecificationsTypeEntity type : types) {
            String value = "";
            for (SpecificationsEntity specification: specifications) {
                if (specification.getType().getDescription().equals(type.getDescription())) {
                    value = specification.getValue();
                }
            }
            resultMap.put(type, value);
        }

        return sortMapByKey(resultMap);
    }

    private static Map<SpecificationsTypeEntity, String> sortMapByKey(Map<SpecificationsTypeEntity, String> map) {
        Map<SpecificationsTypeEntity, String> sortedMap = new TreeMap<>(Comparator.comparing(SpecificationsTypeEntity::getName));
        sortedMap.putAll(map);
        return sortedMap;
    }

    public static String formatAddress(AddressEntity address) {
        return nonNull(address) ? address.getSettlements().getName() + " " +
                (nonNull(address.getStreet()) ? address.getStreet() + " " : "") +
                address.getHouse() : "";
    }

    public static String formatUserUsing(String userUsing) {
        return nonNull(userUsing) ? userUsing : "";
    }

    public static String formatManufacturer(ManufacturerEntity manufacturer) {
        return nonNull(manufacturer) ? manufacturer.getName() : "";
    }

    public static String formatStatus(StatusEntity status) {
        return nonNull(status) ? status.getName() : "";
    }

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return nonNull(date) ? dateFormat.format(date) : "";
    }
}
