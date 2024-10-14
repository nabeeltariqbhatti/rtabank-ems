package ae.rakbank.bookingservice.repository;

import ae.rakbank.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value = """
               SELECT booking from Booking  booking
               left join fetch booking.tickets where booking.id =:bookingId
            """)
    Optional<Booking> getBookingsWithTickets(@Param("bookingId") Long bookingId);

    Booking findBookingByBookingCode(String bookingCode);
}
