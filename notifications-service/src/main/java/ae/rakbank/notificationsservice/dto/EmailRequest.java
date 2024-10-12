package ae.rakbank.notificationsservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

    private Sender sender;
    private List<Recipient> to;
    private String subject;
    
    @JsonProperty("htmlContent")
    private String htmlContent;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sender {
        private String name;
        private String email;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Recipient {
        private String email;
        private String name;
    }
}
