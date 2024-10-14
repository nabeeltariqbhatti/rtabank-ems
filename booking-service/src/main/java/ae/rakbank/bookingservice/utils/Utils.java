package ae.rakbank.bookingservice.utils;

import ae.rakbank.bookingservice.model.Ticket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.UUID;

@Slf4j
public class Utils {

    // Define a date format for generating part of the ticket number
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * Generates a unique ticket number based on current date-time, event ID, and ticket type.
     *
     * @param eventId    The ID of the event.
     * @param ticketType The type of the ticket.
     * @return A unique ticket number.
     */
    public static String generateTicketNumber(Long eventId, String eventCode, Ticket.TicketType ticketType) {
        String timestamp = dateFormat.format(System.currentTimeMillis());
        String ticketTypeCode = ticketType.name().substring(0, 3).toUpperCase();
        String s = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        return s +eventCode+ timestamp+ ticketTypeCode+eventId;
    }

    public static String generateCode(int length) {
        String uuid = UUID.randomUUID().toString().replace("-", ""); // Remove dashes
        Random random = new Random();
        StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(uuid.length());
            code.append(uuid.charAt(index));
        }
        return code.toString();
    }

    /**
     * Generates a unique booking code based on current date-time and event code.
     *
     * @param eventCode The code of the event.
     * @return A unique booking code.
     */
    public static String generateBookingCode(String eventCode) {
        String timestamp = dateFormat.format(System.currentTimeMillis());
        String s = generateCode(4);

        return String.format("%s-%s-%s", s, timestamp, eventCode);
    }


    private static final ObjectMapper objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }
    /**
     * Converts an object to a JSON string.
     *
     * @param object The object to convert.
     * @param <T> The type of the object.
     * @return JSON string representation of the object.
     */
    public static <T> String toJson(T object) {
        if(object == null) return "{}";
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
