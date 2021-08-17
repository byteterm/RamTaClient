package systems.tat.ramta.client.service.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.Scene;
import systems.tat.ramta.client.models.config.CSSInfo;
import systems.tat.ramta.client.models.gui.DisplayScene;
import systems.tat.ramta.client.models.gui.TemplateObject;
import systems.tat.ramta.client.utils.ResourcesUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class FrontendHandler {

    private final File EXTRACT_TEMPLATE_LOCATION = new File(ResourcesUtils.getPath("frontend/displayed/"));

    private final TemplateObject template;

    public FrontendHandler(TemplateObject template) {
        this.template = template;
    }

    public void include(DisplayScene displayScene) {
        String file = null;
        try {
            URL test = new File("frontend/displayed/" + template.getName() + "/css/").toURI().toURL();
            file = "file:///" + test.getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            CSSInfo info = readCSSInfo();
            Scene scene = displayScene.getScene();
            if(displayScene.getName().equalsIgnoreCase("account")) {
                for(String cssName : info.getAccount()) {
                    if(!(scene.getStylesheets().contains(cssName))) {
                        scene.getStylesheets().add(file + cssName);
                        File buildFile = variableCSS(info, file, cssName);
                    }
                }
            } else if(displayScene.getName().equalsIgnoreCase("client")) {
                for(String cssName : info.getClient()) {
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

    private File variableCSS(CSSInfo info, String path, String name) {
        path = path.replace("file:///", "").replaceAll("%20", " ");
        File file = new File(path + name);
        String css = ResourcesUtils.read(file);
        String override = css;
        for(String variables : info.getVariables()) {
            if(variables.contains(":")) {
                String[] array = variables.split(":");
                assert css != null;
                if(css.contains(array[0])) {
                    override = override.replace(array[0], array[1]);
                }
            }
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            assert override != null;
            String[] array = ResourcesUtils.makeArray(override, "[{}]");
            for (String s : array) {
                if(s.startsWith(".")) {
                    writer.write(s + "{");
                    writer.newLine();
                } else {
                    String[] temp = s.split(";");
                    for(int i = 0; i < temp.length; i++) {
                        writer.write(temp[i] + ";");
                        writer.newLine();
                        if(i == (temp.length-1)) {
                            writer.write("}");
                            writer.newLine();
                            writer.newLine();
                        }
                    }
                }
            }
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return file;
    }
}
