package ae.rakbank.eventmanagementservice.schedluer;

import ae.rakbank.eventmanagementservice.dtos.event.UpdateEvent;
import ae.rakbank.eventmanagementservice.mapper.EventMapper;
import ae.rakbank.eventmanagementservice.model.Event;
import io.micrometer.observation.annotation.Observed;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class EventScheduler {


    @Scheduled(fixedRate = 10000)
    @Transactional
    @Observed
    public void checkForExpiredEvents() {
        log.info("Expire Done events and send notification to booking service");
        LocalDateTime now = LocalDateTime.now();
        List<Event> expiredEvents = new ArrayList<>();
        for (Event event : expiredEvents) {


        }
    }


}
