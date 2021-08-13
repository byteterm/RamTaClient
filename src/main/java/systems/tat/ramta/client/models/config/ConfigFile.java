package systems.tat.ramta.client.models.config;

import systems.tat.ramta.client.enums.FileTypes;
import systems.tat.ramta.client.service.settings.handler.*;

import java.io.File;

public class ConfigFile {

    private final String name;
    private final String fileName;
    private final String path;
    private final String parentPath;
    private final String type;

    private final FileTypes handlerType;

    public ConfigFile(String name, String path) {
        String[] temp = name.split("\\.");
        this.name = temp[0];
        this.fileName = name;
        this.path = path;
        this.parentPath = new File(path).getParent();
        this.type = temp[1];
        this.handlerType = FileTypes.getByShortCut(type);
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    public String getParentPath() {
        return parentPath;
    }

    public String getType() {
        return type;
    }

    public FileTypes getHandlerType() {
        return handlerType;
    }

    public IFileHandler getHandler() {
        IFileHandler handler = null;
        switch (handlerType) {
            case PROPERTIES -> {
                handler = new PropertiesHandler(path);
            }
            case JSON -> {
                handler = new JSONHandler(path);
            }
            case INITIALIZE -> {
                handler = new INIHandler(path);
            }
            default -> handler = new TEXTHandler(path);
        }
        return handler;
    }
}
