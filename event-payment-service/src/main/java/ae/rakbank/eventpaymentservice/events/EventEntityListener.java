package ae.rakbank.eventpaymentservice.events;

import ae.rakbank.eventpaymentservice.mapper.PaymentMapper;
import ae.rakbank.eventpaymentservice.models.Purchase;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@NoArgsConstructor
public class EventEntityListener {

    @Autowired
    private   EventProducer eventProducer;

    @Value(value = "${rakbank.events.topic.payment.to.booking.topic}")
    private String topic;


    @PrePersist
    public void beforeCreate(Purchase event) {
        System.out.println("Event is about to be created: " + event.getPaymentStatus());

    }

    @PostPersist
    @Async
    public void afterCreate(Purchase event) {
        System.out.println("Purchase created: " + event.getPurchaseCode());
        eventProducer.produce(PaymentMapper.toPaymentEvent(event),topic);
    }

    @PostUpdate
    public void afterUpdate(Purchase event) {
        System.out.println("Purchase updated " + event.getPurchaseCode());
        eventProducer.produce(PaymentMapper.toPaymentEvent(event),topic);
    }
}
