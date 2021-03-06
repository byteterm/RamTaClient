package systems.tat.ramta.client.controller.gui;

import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import systems.tat.ramta.client.event.StageReadyEvent;
import systems.tat.ramta.client.service.gui.DisplayService;
import systems.tat.ramta.client.service.socket.SocketClientService;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private final FxWeaver fxWeaver;
    private static DisplayService displayService;
    private final SocketClientService socketClientService;

    public StageInitializer(FxWeaver fxWeaver, DisplayService service, SocketClientService socketClientService) {
        this.fxWeaver = fxWeaver;
        displayService = service;
        this.socketClientService = socketClientService;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {

        Stage stage = event.getStage();
        displayService.initialize(stage, fxWeaver);
        displayService.displayScene("Account");
        socketClientService.connect();
        /*
         * stage.setScene(new Scene(fxWeaver.loadView(AccountController.class)));
         * stage.show();
         */
    }

    public static DisplayService getDisplayService() {
        return displayService;
    }
}
