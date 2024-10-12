package ae.rakbank.eventbookingservice.events;


import ae.rakbank.eventbookingservice.cache.EventCache;
import ae.rakbank.eventbookingservice.dto.event.BookingEvent;
import ae.rakbank.eventbookingservice.dto.event.EventMetadata;
import ae.rakbank.eventbookingservice.mapper.BookingMapper;
import ae.rakbank.eventbookingservice.model.Booking;
import io.micrometer.observation.annotation.Observed;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * TODO: update cache
 */

@Component
@Slf4j
@NoArgsConstructor
public class BookingEntityListener {


    @Autowired
    private KafkaEventProducer kafkaProducer;
    @Value(value = "${rakbank.events.topic.booking.to.payment.topic}")
    private String bookingToPayments;

    @PrePersist
    @Observed(name = "create.booking",
            contextualName = "creating-booking"
    )
    public void beforeCreate(Booking event) {
        log.info("Booking is about to be created: ");

    }

    @PostPersist
    @Observed(name = "create.booking",
            contextualName = "creating-booking"
    )
    public void afterCreate(Booking event) {
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


    }

    @PreUpdate
    public void beforeUpdate(Booking event) {
        log.info("Event is about to be updated: ");
    }

    @PostUpdate
    public void afterUpdate(Booking event) {
        log.info("Event updated successfully:{} ", event);
        BookingEvent bookingEvent = BookingMapper.toBookingEvent(event);
        log.info("producing event on {} with {} ", bookingToPayments, bookingEvent);
        kafkaProducer.produce(bookingEvent, bookingToPayments);
    }
}
