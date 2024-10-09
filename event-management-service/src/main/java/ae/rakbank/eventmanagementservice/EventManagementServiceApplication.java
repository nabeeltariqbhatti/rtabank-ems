package ae.rakbank.eventmanagementservice;

import ae.rakbank.eventmanagementservice.config.EventManagementConfigurations;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySources;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableConfigurationProperties(EventManagementConfigurations.class)
@EnableRetry
@EnableFeignClients
@EnableAsync
public class EventManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventManagementServiceApplication.class, args);

    }

}
