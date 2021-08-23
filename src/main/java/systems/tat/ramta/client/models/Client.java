package systems.tat.ramta.client.models;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Data
@Component
public class Client {

    private String username;
    private String nameId;
    private String uuid;
    private String password;
    private String email;
    private String image;

    private long AcceptAGB;
}
