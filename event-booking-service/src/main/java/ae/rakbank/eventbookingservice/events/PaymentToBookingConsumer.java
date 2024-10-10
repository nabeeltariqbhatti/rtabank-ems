package ae.rakbank.eventbookingservice.events;

import ae.rakbank.eventbookingservice.dto.event.PaymentEvent;
import ae.rakbank.eventbookingservice.model.Booking;
import ae.rakbank.eventbookingservice.service.BookingService;
import ae.rakbank.eventbookingservice.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public final class PaymentToBookingConsumer implements EventListener<ConsumerRecord<String,String>> {

    @Autowired
    private final BookingService bookingService;

    public PaymentToBookingConsumer(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @KafkaListener(topics = "${rakbank.events.topic.payment.to.booking.topic}", groupId = "event-management")
    public void consumeEvent(ConsumerRecord<String,String> event) {
        if(event == null) return;
        PaymentEvent paymentEvent = Utils.toObject(event.value(), PaymentEvent.class);
        switch (Objects.requireNonNull(paymentEvent).getPaymentStatus()){
            case PAID ,REFUNDED-> {
                Booking byBookingCode = bookingService.getByBookingCode(paymentEvent.getBookingCode());
                byBookingCode.setPaymentStatus(paymentEvent.getPaymentStatus());
                if(Booking.PaymentStatus.PAID.equals(paymentEvent.getPaymentStatus())){
                    byBookingCode.setStatus(Booking.Status.CONFIRMED);
                }
                bookingService.updateBooking(byBookingCode.getId(),byBookingCode);
            }
            case NOT_RECEIVED,UNPAID -> log.info("don't change booking status");
            default -> log.info("no known action to be performed for the event. " + paymentEvent);
        }
        log.info("Received event: " + paymentEvent);
    }



}
