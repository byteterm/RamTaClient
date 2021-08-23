package systems.tat.ramta.client.models;

import org.bson.types.Binary;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Data
@Component
public class Client {

    private String username;
    private String password;
    private String email;
    private String image;

    private long AcceptAGB;
}
