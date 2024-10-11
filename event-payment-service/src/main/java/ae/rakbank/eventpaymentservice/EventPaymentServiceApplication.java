package ae.rakbank.eventpaymentservice;

import io.micrometer.observation.annotation.Observed;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
@Observed
public class EventPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventPaymentServiceApplication.class, args);
	}

}
