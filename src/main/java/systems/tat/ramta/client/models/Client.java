package systems.tat.ramta.client.models;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Client {

    private String username;
    private String password;
    private String email;

    private long AcceptAGB;
}
