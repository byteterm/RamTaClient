package systems.tat.ramta.client.service.settings.handler;

import java.io.File;

public class JSONHandler implements IFileHandler{

    private final File jsonFile;

    public JSONHandler(String path) {
        this.jsonFile = new File(path);
    }

}
