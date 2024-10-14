package ae.rakbank.bookingservice.events;


import ae.rakbank.bookingservice.utils.Utils;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component("kafkaEventProducer")
@Slf4j
public final class KafkaEventProducer implements EventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final Tracer tracer;

    public KafkaEventProducer(KafkaTemplate<String, Object> kafkaTemplate, Tracer tracer) {
        this.kafkaTemplate = kafkaTemplate;
        this.tracer = tracer;
    }


    @Override
    public <T> void produce(T event, String topic) {
        Span newSpan = this.tracer.nextSpan().name("producing event");
        try (Tracer.SpanInScope ws = this.tracer.withSpan(newSpan.start())) {
            newSpan.tag("topic", topic);
            newSpan.tag("event", Utils.toJson(event));
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, event);
            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    newSpan.error(ex);
                    log.error("Message failed to send", ex);
                } else {
                    log.info("Message sent successfully: {}", result.getProducerRecord().value());
                }
            });
            newSpan.event("event produced");
        } finally {

            newSpan.end();
        }

    }


}
