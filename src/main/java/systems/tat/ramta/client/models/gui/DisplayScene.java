package systems.tat.ramta.client.models.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import net.rgielen.fxweaver.core.FxWeaver;

public class DisplayScene {

    public static FxWeaver fxmlWeaver;
    private final String name;
    private Scene scene;

    public DisplayScene(String name, FxWeaver weaver) {
        this.name = name;
        fxmlWeaver = weaver;
        try {
            this.scene = new Scene(weaver.loadView(Class.forName("systems.tat.ramta.client.controller.gui." + name + "Controller")));
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public FxWeaver getFxmlWeaver() {
        return fxmlWeaver;
    }

    public Scene getScene() {
        return scene;
    }
}
