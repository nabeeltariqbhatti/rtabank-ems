package ae.rakbank.eventmanagementservice;

import ae.rakbank.eventmanagementservice.config.EventManagementConfigurations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(EventManagementConfigurations.class)
@EnableRetry
@EnableAsync
@EnableScheduling
public class EventManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventManagementServiceApplication.class, args);

    }

}
