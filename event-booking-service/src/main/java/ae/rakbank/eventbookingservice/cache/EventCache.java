package ae.rakbank.eventbookingservice.cache;

import ae.rakbank.eventbookingservice.dto.event.EventMetadata;
import lombok.extern.slf4j.Slf4j;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class EventCache {

    // Cache to store Event metadata using eventCode as key and metadata as value
    private static final Map<String, WeakReference<EventMetadata>> eventCache = new ConcurrentHashMap<>();

    /**
     * Add or update event metadata in the cache.
     *
     * @param eventCode The event code (unique identifier).
     * @param eventMetadata The metadata of the event.
     */
    public static void updateEvent(String eventCode, EventMetadata eventMetadata) {
        log.info("Updating cache for event: {}", eventCode);
        eventCache.put(eventCode, new WeakReference<>(eventMetadata));
    }

    /**
     * Get event metadata from cache.
     *
     * @param eventCode The event code (unique identifier).
     * @return The metadata of the event or null if not found.
     */
    public static EventMetadata getEvent(String eventCode) {
        WeakReference<EventMetadata> weakReference = eventCache.get(eventCode);
        return weakReference != null ? weakReference.get() : null;
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
    public static Map<String, WeakReference<EventMetadata>> getAll(){
        return eventCache;
    }
}
