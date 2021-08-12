package systems.tat.ramta.client.service.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Service;
import systems.tat.ramta.client.models.gui.DisplayScene;
import systems.tat.ramta.client.models.gui.TemplateObject;
import systems.tat.ramta.client.utils.ResourcesUtils;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
public class DisplayService {

    private final List<TemplateObject> templates = new ArrayList<>();
    private final Map<String, DisplayScene> scenes = new HashMap<>();
    private final File RESOURCE_LOCATION = new File(ResourcesUtils.getExecutePath().getPath() + "/frontend/templates/");

    private Stage stage;
    private FxWeaver weaver;

    public void initialize(Stage stage, FxWeaver weaver) {
        this.stage = stage;
        this.setup();
        this.weaver = weaver;
    }

    public void displayScene(String name) {
        DisplayScene displayScene = scenes.get(name);
        stage.setScene(displayScene.getScene());
        stage.show();
    }

    public void addScene(DisplayScene scene) {
        if(!(scenes.containsKey(scene.getName()))) {
            scenes.put(scene.getName(), scene);
        }
    }

    public void removeScene(String name) {
        if(scenes.containsKey(name)) {
            this.removeScene(name);
        }
    }

    public void reloadTemplates() {
        this.loadFromFolder();
    }

    public int getTemplateSize() {
        return templates.size();
    }

    public FxWeaver getWeaver() {
        return weaver;
    }

    public Stage getStage() {
        return stage;
    }

    private void setup() {
        this.stage.initStyle(StageStyle.DECORATED);
        this.loadDefaultScenes();
        this.loadFromFolder();
        System.out.println("Temp -> " + templates.isEmpty());
        for (TemplateObject object : templates) {
            System.out.println("Name: " + object.getName());
        }
        //System.out.println(templates.get(0).getName() + "," + templates.get(0).getVersion());
    }

    private void loadDefaultScenes() {
        DisplayScene accountScene = new DisplayScene("account", "/fxml/account.fxml");
        scenes.put(accountScene.getName(), accountScene);
        DisplayScene clientScene = new DisplayScene("client", "/fxml/client.fxml");
        scenes.put(clientScene.getName(), clientScene);
    }

    private void loadFromFolder() {
        for(File file : Objects.requireNonNull(RESOURCE_LOCATION.listFiles())) {
            if(ResourcesUtils.trueFormat(file, ".zip")) {
                TemplateObject template = readTemplateInfo(file);
                if(!(templates.contains(template))) {
                    templates.add(template);
                }
            }
        }
    }

    private TemplateObject readTemplateInfo(File file) {
        try {
            ZipFile zipFile = new ZipFile(file);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            TemplateObject template = null;
            ObjectMapper mapper = new ObjectMapper();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if(!(entry.isDirectory()) & entry.getName().endsWith("templateInfo.json")) {
                    template = mapper.readValue(jsonString(entry, zipFile.getInputStream(entry)), TemplateObject.class);
                }
            }
            return template;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private String jsonString(ZipEntry entry, InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String result;
        StringBuilder builder = new StringBuilder();
        while ((result = reader.readLine()) != null) {
            builder.append(result);
        }
        reader.close();
        return builder.toString();
    }
}
