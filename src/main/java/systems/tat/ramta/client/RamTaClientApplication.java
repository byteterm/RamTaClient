package systems.tat.ramta.client;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RamTaClientApplication {

    public static void main(String[] args) {
        Application.launch(RamTaClientFX.class, args);
    }

}
