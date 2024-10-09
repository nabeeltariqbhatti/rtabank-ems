package ae.rakbank.eventmanagementservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class Utils {
    public ObjectMapper objectMapper(){
        return new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
    public static String generateEventCode(String eventName, String provider) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String formattedDateTime = now.format(formatter);
        String eventCode = eventName.toUpperCase() + formattedDateTime + "P-" + provider.toUpperCase();

        return eventCode;
    }
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts an object to a JSON string.
     *
     * @param object The object to convert.
     * @param <T> The type of the object.
     * @return JSON string representation of the object.
     */
    public static <T> String toJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error converting object to JSON", e);
            return null; // Or throw a custom exception depending on your use case.
        }
    }

    /**
     * Converts a JSON string to an object of the specified class type.
     *
     * @param json The JSON string.
     * @param clazz The class of the object to convert to.
     * @param <T> The type of the object.
     * @return The object of type T.
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Error converting JSON to object", e);
            return null; // Or throw a custom exception depending on your use case.
        }
    }

}
