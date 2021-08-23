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
    private Binary image;

    private long AcceptAGB;

    public String getImage() {
        if (image != null) {
            return Base64.getEncoder().encodeToString(image.getData());
        }

        return null;
    }
}
