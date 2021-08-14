package systems.tat.ramta.client.service.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.Scene;
import systems.tat.ramta.client.models.config.CSSInfo;
import systems.tat.ramta.client.models.gui.DisplayScene;
import systems.tat.ramta.client.models.gui.TemplateObject;
import systems.tat.ramta.client.utils.ResourcesUtils;

import java.io.File;
import java.io.IOException;

public class FrontendService {

    private final File EXTRACT_TEMPLATE_LOCATION = new File(ResourcesUtils.getExecutePath().getPath() + "/frontend/displayed/");

    private final TemplateObject template;

    public FrontendService(TemplateObject template) {
        this.template = template;
    }

    public void include(DisplayScene displayScene) {
        String file = "file:///" + EXTRACT_TEMPLATE_LOCATION.getPath().replace("\\", "/") + "/" + template.getName() + "/css/";
        try {
            CSSInfo info = readCSSInfo();
            Scene scene = displayScene.getScene();
            if(displayScene.getName().equalsIgnoreCase("account")) {
                for(String cssName : info.getAccount()) {
                    if(!(scene.getStylesheets().contains(cssName))) {
                        scene.getStylesheets().add(file + cssName);
                    }
                }
            } else if(displayScene.getName().equalsIgnoreCase("client")) {
                for(String cssName : info.getAccount()) {
                    if(!(scene.getStylesheets().contains(cssName))) {
                        scene.getStylesheets().add(file + cssName);
                    }
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private CSSInfo readCSSInfo() throws IOException {
        File file = new File(EXTRACT_TEMPLATE_LOCATION.getPath() + "/" + template.getName() + "/css/cssInfo.json");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, CSSInfo.class);
    }
}
