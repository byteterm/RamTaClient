package systems.tat.ramta.client.models.lang;

import systems.tat.ramta.client.service.settings.handler.JSONHandler;
import systems.tat.ramta.client.utils.ResourcesUtils;

import java.io.File;
import java.util.Objects;

public class Language {

    private final File RESOURCE_LOCATION = new File(ResourcesUtils.getPath("frontend/displayed"));

    private final String name;
    private final String unicode;
    private final String flagIconPath;
    private final File icon;
    private final File languageFile;

    public Language(File languageFile) {
        this.languageFile = languageFile;
        String[] object = languageFile.getName().split("\\.");
        this.name = object[0];
        this.unicode = read("unicode");
        File file = null;
        for(File files : Objects.requireNonNull(RESOURCE_LOCATION.listFiles())) {
            file = files;
        }
        assert file != null;
        this.flagIconPath = RESOURCE_LOCATION.getPath() + "/" + file.getName() + "/" + read("flagIcon");
        this.icon = new File(flagIconPath);
    }

    public String getName() {
        return name;
    }

    public String getUnicode() {
        return unicode;
    }

    public File getLanguageFile() {
        return languageFile;
    }

    public String getFlagIconPath() {
        return flagIconPath;
    }

    public String getFlagURL() {
        return "file:///" + flagIconPath.replaceAll(" ", "%20");
    }

    public File getIcon() {
        return icon;
    }

    private String read(String key) {
        JSONHandler handler = new JSONHandler(this.languageFile.getPath());
        return handler.getString(key);
    }

}
