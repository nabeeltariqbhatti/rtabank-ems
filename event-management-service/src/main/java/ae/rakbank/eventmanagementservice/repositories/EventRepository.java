package ae.rakbank.eventmanagementservice.repositories;

import ae.rakbank.eventmanagementservice.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {


    @Query(value = """
            SELECT count(event.id) FROM Event event
            WHERE event.venue = :venue
            AND (event.startDateTime <= :endDateTime AND event.endDateTime >= :startDateTime)
            """)
    int  existsEventByVenueAndStartDateTimeBetween(String venue, LocalDateTime startDateTime, LocalDateTime endDateTime);


    @Query("SELECT e FROM Event e WHERE e.name LIKE %:searchTerm%")
    Page<Event> findAllByNameLike(@Param("searchTerm") String searchTerm, Pageable pageable);

}
