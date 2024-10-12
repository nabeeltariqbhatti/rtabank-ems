package ae.rakbank.accountmanagementservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDTO {

    private Long id;
    private String lastName;
    private String firstName;
    @Email
    private String email;
    private String phone;
    @Past
    private LocalDate birthDate;
    private LocalDateTime registeredAt;
}
