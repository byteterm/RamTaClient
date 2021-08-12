package systems.tat.ramta.client;

import javafx.application.Application;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RamTaClientApplication {

    public static void main(String[] args) {
        Application.launch(RamTaClientFX.class, args);
    }

}
