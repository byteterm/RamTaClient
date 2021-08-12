package systems.tat.ramta.client.service.gui;

import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Service;
import systems.tat.ramta.client.models.gui.TemplateObject;

import java.util.ArrayList;
import java.util.List;

@Service
public class DisplayService {

    private final List<TemplateObject> templates = new ArrayList<>();

    private Stage stage;
    private FxWeaver weaver;

    public void initialize(Stage stage, FxWeaver weaver) {
        this.stage = stage;
        this.weaver = weaver;
    }

    public FxWeaver getWeaver() {
        return weaver;
    }

    public Stage getStage() {
        return stage;
    }
}
