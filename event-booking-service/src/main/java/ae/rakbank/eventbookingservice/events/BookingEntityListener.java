package ae.rakbank.eventbookingservice.events;


import ae.rakbank.eventbookingservice.cache.EventCache;
import ae.rakbank.eventbookingservice.dto.event.EventMetadata;
import ae.rakbank.eventbookingservice.model.Booking;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * TODO: update cache
 */

@Component
@Slf4j
@NoArgsConstructor
public class BookingEntityListener {





    @PrePersist
    public void beforeCreate(Booking event) {
        System.out.println("Event is about to be created: ");

    }

    @PostPersist
    public void afterCreate(Booking event) {

        //TODO: handle other status types
        /***
         * confirm -> booking is updated
         * increment confirmed seats
         *
         * cancelled -> generated refund event
         *
         * send booking data such as invalid after and create payment id in payment
         */
        EventMetadata eventMetadata = EventCache.getEvent(event.getEventCode());
       if(Booking.Status.PENDING.equals(event.getStatus())){
           int availableTickets = eventMetadata.getAvailableTickets() - event.getTickets().size();
           int reservedSeats = eventMetadata.getReservedSeats()+event.getTickets().size();
           eventMetadata.setAvailableTickets(availableTickets);
           eventMetadata.setReservedSeats(reservedSeats);
       }
       //TODO: create payment event

    }

    @PreUpdate
    public void beforeUpdate(Booking event) {
        System.out.println("Event is about to be updated: " );
    }

    @PostUpdate
    public void afterUpdate(Booking event) {
        System.out.println("Event updated successfully: " );
    }
}
