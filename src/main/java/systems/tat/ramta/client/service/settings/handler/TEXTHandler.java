package systems.tat.ramta.client.service.settings.handler;

import java.io.File;

public class TEXTHandler implements IFileHandler {

    private final File textFile;

    public TEXTHandler(String path) {
        this.textFile = new File(path);
    }

}
