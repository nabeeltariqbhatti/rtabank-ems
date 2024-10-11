package ae.rakbank.notificationsservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@Slf4j
public class Utils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }


    private Utils() {}
    /**
     * Converts an object to a JSON string.
     *
     * @param object The object to convert.
     * @param <T> The type of the object.
     * @return JSON string representation of the object.
     */
    public static <T> String toJson(T object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
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
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Error converting JSON to object", e);
            return null; // Or throw a custom exception depending on your use case.
        }
    }
}
