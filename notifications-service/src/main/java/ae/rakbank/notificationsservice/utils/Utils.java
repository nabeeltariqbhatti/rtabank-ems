package ae.rakbank.notificationsservice.utils;

import ae.rakbank.notificationsservice.model.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Component
@Slf4j
public class Utils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

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

    public static String getNotificationMessage(Notification.NotificationType notificationType, String bookingCode) {
        return switch (notificationType) {
            case PENDING ->
                    "Your booking is pending. Please make the payment to confirm your booking. Booking code: " + bookingCode;
            case CONFIRMED ->
                    "Your booking has been confirmed! We look forward to seeing you at the event. Booking code: " + bookingCode;
            case CANCELED ->
                    "Your booking has been canceled. If this was a mistake, please contact our support team. Booking code: " + bookingCode;
            case COMPLETED ->
                    "Thank you for attending the event! Your booking is marked as completed. Booking code: " + bookingCode;
            case GONE ->
                    "The event has already taken place, and your booking is no longer valid. Booking code: " + bookingCode;
        };
    }

    private static DefaultResourceLoader resourceLoader= new DefaultResourceLoader();

    public static  String readAsString(String path){
        try{
            return resourceLoader.getResource(path)
                    .getContentAsString(StandardCharsets.UTF_8);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return """
                You Booking status is %s
                
                """.formatted("Alpha");

    }

}
