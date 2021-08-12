package systems.tat.ramta.client.models.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class DisplayScene {

    public static String fxmlPath;
    private final String name;
    private Scene scene;

    public DisplayScene(String name, String path) {
        this.name = name;
        fxmlPath = path;
        try {
            this.scene = new Scene(FXMLLoader.load(Objects.requireNonNull(DisplayScene.class.getResource(path))));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }

    public Scene getScene() {
        return scene;
    }
}
