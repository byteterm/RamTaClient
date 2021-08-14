package systems.tat.ramta.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import systems.tat.ramta.client.event.StageReadyEvent;
import systems.tat.ramta.client.service.settings.SettingService;

public class RamTaClientFX extends Application {

    private ConfigurableApplicationContext applicationContext;

    private static SettingService settingService;

    @Override
    public void init() throws Exception {
        applicationContext = new SpringApplicationBuilder(RamTaClientApplication.class).run();
        settingService = new SettingService();
    }

    @Override
    public void start(Stage stage) throws Exception {
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() throws Exception {
        applicationContext.close();
        Platform.exit();
    }

    public static SettingService getSettingService() {
        return settingService;
    }
}
