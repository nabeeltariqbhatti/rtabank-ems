package ae.rakbank.eventmanagementservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("bookingClient")
public interface BookingClient {
}
