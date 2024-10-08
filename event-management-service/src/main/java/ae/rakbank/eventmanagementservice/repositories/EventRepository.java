package ae.rakbank.eventmanagementservice.repositories;

import ae.rakbank.eventmanagementservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // You can define additional query methods here if needed
}
