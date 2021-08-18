package systems.tat.ramta.client.service.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Service;
import systems.tat.ramta.client.models.config.ConfigFile;
import systems.tat.ramta.client.models.gui.DisplayScene;
import systems.tat.ramta.client.models.gui.TemplateObject;
import systems.tat.ramta.client.service.settings.SettingService;
import systems.tat.ramta.client.service.settings.handler.PropertiesHandler;
import systems.tat.ramta.client.utils.ResourcesUtils;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
public class DisplayService {

    private final List<TemplateObject> templates = new ArrayList<>();
    private final Map<String, DisplayScene> scenes = new HashMap<>();
    private final File RESOURCE_LOCATION = new File(ResourcesUtils.getPath("frontend/templates/"));
    private final File EXTRACT_TEMPLATE_LOCATION = new File(ResourcesUtils.getPath("frontend/displayed/"));

    private Stage stage;
    private FxWeaver weaver;
    private TemplateObject currentTemplate;

    private final SettingService settingService;

    public DisplayService(SettingService settingService) {
        this.settingService = settingService;
    }

    public void initialize(Stage stage, FxWeaver weaver) {
        this.stage = stage;
        this.weaver = weaver;
        this.setup();
    }

    public void displayScene(String name) {
        DisplayScene displayScene = scenes.get(name);
        FrontendHandler service = new FrontendHandler(currentTemplate);
        service.include(displayScene);
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

    public boolean existTemplate(String templateName) {
        for(TemplateObject object : templates) {
            if(object.getName().equals(templateName)) {
                return true;
            }
        }
        return false;
    }

    public TemplateObject getTemplateObject(String name) {
        for(TemplateObject object : templates) {
            if(object.getName().equals(name)) {
                return object;
            }
        }
        return null;
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

    public TemplateObject getCurrentTemplate() {
        return currentTemplate;
    }

    private void setup() {
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.loadDefaultScenes();
        this.loadFromFolder();
        this.catchInfo();
        this.extractTemplate();
    }

    private void catchInfo() {
        SettingService service = settingService;

        if(!(service.getConfigs().containsKey("generalConf"))) {
            currentTemplate = getTemplateObject("default");
            return;
        }

        ConfigFile config = service.getConfigs().get("generalConf");
        PropertiesHandler handler = (PropertiesHandler) config.getHandler();
        String designName = handler.getString("usedTemplate");
        if(existTemplate(designName)) {
            currentTemplate = getTemplateObject(designName);
        }
    }

    private void loadDefaultScenes() {
        DisplayScene accountScene = new DisplayScene("Account", weaver);
        scenes.put(accountScene.getName(), accountScene);
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

    private void clearDisplayedFolder() {
        if(Objects.requireNonNull(EXTRACT_TEMPLATE_LOCATION.listFiles()).length > 0) {
            for(File file : Objects.requireNonNull(EXTRACT_TEMPLATE_LOCATION.listFiles())) {
                file.delete();
            }
        }
    }

    private void extractTemplate() {
        try {
            this.clearDisplayedFolder();
            File zipPath = new File(RESOURCE_LOCATION.getPath() + "/" + currentTemplate.getName() + ".zip");
            ResourcesUtils.unzip(zipPath, EXTRACT_TEMPLATE_LOCATION);
        } catch (IOException exception) {
            exception.printStackTrace();
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
