package ae.rakbank.bookingservice.events;

import ae.rakbank.bookingservice.cache.EventCache;
import ae.rakbank.bookingservice.dto.event.EventMetadata;
import ae.rakbank.bookingservice.dto.event.NotificationDTO;
import ae.rakbank.bookingservice.dto.event.PaymentEvent;
import ae.rakbank.bookingservice.model.Booking;
import ae.rakbank.bookingservice.service.BookingService;
import ae.rakbank.bookingservice.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public final class PaymentToBookingConsumer implements EventListener<ConsumerRecord<String,String>> {


    private final BookingService bookingService;
    private final KafkaEventProducer kafkaEventProducer;
    @Value(value = "${rakbank.events.topic.booking.to.notifications.topic}")
    private String bookingToNotificationsTopic;

    @Autowired
    public PaymentToBookingConsumer(BookingService bookingService, KafkaEventProducer kafkaEventProducer) {
        this.bookingService = bookingService;
        this.kafkaEventProducer = kafkaEventProducer;
    }

    @KafkaListener(topics = "${rakbank.events.topic.payment.to.booking.topic}", groupId = "event-management")
    public void consumeEvent(ConsumerRecord<String,String> event) {
        if(event == null) return;
        PaymentEvent paymentEvent = Utils.toObject(event.value(), PaymentEvent.class);
        Booking byBookingCode = bookingService.getByBookingCode(paymentEvent.getBookingCode());
        EventMetadata eventMetadata = EventCache.getEvent(byBookingCode.getEventCode());
        switch (Objects.requireNonNull(paymentEvent).getPaymentStatus()){
            case PAID ,REFUNDED-> {
                byBookingCode.setPaymentStatus(paymentEvent.getPaymentStatus());
                if(Booking.PaymentStatus.PAID.equals(paymentEvent.getPaymentStatus())){
                    byBookingCode.setStatus(Booking.Status.CONFIRMED);
                    if(eventMetadata.getAvailableTickets() == byBookingCode.getTicketQuantity()){
                         EventCache.removeEvent(eventMetadata.getEventCode());
                    }else{
                        eventMetadata.setReservedSeats(
                                EventCache.getEvent(paymentEvent.getEventCode())
                                        .getReservedSeats()-byBookingCode.getTicketQuantity());
                        eventMetadata.setReservedSeats(
                                EventCache.getEvent(paymentEvent.getEventCode())
                                        .getAvailableTickets()-byBookingCode.getTicketQuantity());
                        eventMetadata.setReservedSeats(
                                EventCache.getEvent(paymentEvent.getEventCode())
                                        .getConfirmedSeats()+byBookingCode.getTicketQuantity());
                    }

                }else{

                    //TODO: handle refund
                }
                bookingService.updateBooking(byBookingCode.getId(),byBookingCode);
            }
            case NOT_RECEIVED,UNPAID -> log.info("don't change booking status");
            default -> log.info("no known action to be performed for the event. " + paymentEvent);

        }
        NotificationDTO build = NotificationDTO.builder()
                .paymentAmount(paymentEvent.getTotalAmount())
                .bookingCode(byBookingCode.getBookingCode())
                .notificationType(NotificationDTO.NotificationType.valueOf(byBookingCode.getStatus().name()))
                .eventDate(eventMetadata.getStartDateTime())
                .eventName(eventMetadata.getEventName())
                .numberOfTickets(byBookingCode.getTicketQuantity())
                .eventLocation(eventMetadata.getVenue())
                .fullName(byBookingCode.getFullName())
                .userName(byBookingCode.getUsername()).build();
        kafkaEventProducer.produce(build,bookingToNotificationsTopic);
        log.info("Received event: " + paymentEvent);
    }



}
