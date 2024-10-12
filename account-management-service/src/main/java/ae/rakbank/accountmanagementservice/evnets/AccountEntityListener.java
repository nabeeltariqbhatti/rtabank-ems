package ae.rakbank.accountmanagementservice.evnets;


import ae.rakbank.accountmanagementservice.model.Account;
import io.micrometer.observation.annotation.Observed;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@NoArgsConstructor
public class AccountEntityListener {




    @PostPersist
    public void afterCreate(Account event) {
        log.debug("user created {} ", event.getEmail());

    }


}
