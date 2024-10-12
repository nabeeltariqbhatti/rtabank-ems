package ae.rakbank.eventbookingservice.events;


import ae.rakbank.eventbookingservice.cache.EventCache;
import ae.rakbank.eventbookingservice.dto.event.BookingEvent;
import ae.rakbank.eventbookingservice.dto.event.EventMetadata;
import ae.rakbank.eventbookingservice.mapper.BookingMapper;
import ae.rakbank.eventbookingservice.model.Booking;
import ae.rakbank.eventbookingservice.utils.Utils;
import io.micrometer.observation.annotation.Observed;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * TODO: update cache
 */

@Component
@Slf4j
@NoArgsConstructor
public class BookingEntityListener {
    @Autowired
    private Tracer tracer;

    @Autowired
    private KafkaEventProducer kafkaProducer;
    @Value(value = "${rakbank.events.topic.booking.to.payment.topic}")
    private String bookingToPayments;


    @PostPersist
    public void afterCreate(Booking event) {
        Span newSpan = this.tracer.nextSpan().name("producing event");
        try  {
            EventMetadata eventMetadata = EventCache.getEvent(event.getEventCode());
            if (eventMetadata != null) {
                int availableTickets = eventMetadata.getAvailableTickets() - event.getTickets().size();
                int reservedSeats = eventMetadata.getReservedSeats() + event.getTickets().size();
                eventMetadata.setAvailableTickets(availableTickets);
                eventMetadata.setReservedSeats(reservedSeats);
                EventCache.updateEvent(eventMetadata.getEventCode(), eventMetadata);
                log.info("send booking to payment service to expect payment");
            }
            kafkaProducer.produce(BookingMapper.toBookingEvent(event), bookingToPayments);
        } catch (Exception exception) {
            newSpan.error(exception);
        } finally {
            newSpan.end();
        }

    }

    @PostUpdate
    public void afterUpdate(Booking event) {
        Span newSpan = this.tracer.nextSpan().name("producing event");
        try {
            log.info("Event updated successfully:{} ", event);
            BookingEvent bookingEvent = BookingMapper.toBookingEvent(event);
            log.debug("producing event on {} with {} ", bookingToPayments, bookingEvent);
            kafkaProducer.produce(bookingEvent, bookingToPayments);
        } catch (Exception exception) {
            newSpan.error(exception);
        } finally {
            newSpan.end();
        }

    }
}
