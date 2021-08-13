package systems.tat.ramta.client.service.settings.handler;

import java.io.File;

public class INIHandler implements IFileHandler {

    private final File iniFile;

    public INIHandler(String path) {
        this.iniFile = new File(path);
    }

}
