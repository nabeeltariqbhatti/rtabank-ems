package ae.rakbank.eventpaymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class EventPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventPaymentServiceApplication.class, args);
	}

}
