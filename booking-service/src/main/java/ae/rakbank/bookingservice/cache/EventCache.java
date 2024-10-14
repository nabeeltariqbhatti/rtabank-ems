package ae.rakbank.bookingservice.cache;

import ae.rakbank.bookingservice.dto.event.EventMetadata;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class EventCache {

    // Cache to store Event metadata using eventCode as key and metadata as value
    private static final Map<String, EventMetadata> eventCache = new ConcurrentHashMap<>();

    /**
     * Add or update event metadata in the cache.
     *
     * @param eventCode     The event code (unique identifier).
     * @param eventMetadata The metadata of the event.
     */
    public static void updateEvent(String eventCode, EventMetadata eventMetadata) {
        log.info("Updating cache for event: {}", eventCode);
        eventCache.put(eventCode, eventMetadata);
    }

    /**
     * Get event metadata from cache.
     *
     * @param eventCode The event code (unique identifier).
     * @return The metadata of the event or null if not found.
     */
    public static EventMetadata getEvent(String eventCode) {
        return eventCache.get(eventCode);

    }

    /**
     * Remove event from the cache.
     *
     * @param eventCode The event code (unique identifier).
     */
    public static void removeEvent(String eventCode) {
        log.info("Removing event from cache: {}", eventCode);
        eventCache.remove(eventCode);
    }

    /**
     * Clears the entire cache.
     */
    public static void clearCache() {
        log.info("Clearing the entire event cache");
        eventCache.clear();
    }

    public static Map<String, EventMetadata> getAll() {
        return eventCache;
    }
}
