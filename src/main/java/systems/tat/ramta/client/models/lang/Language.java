package systems.tat.ramta.client.models.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import systems.tat.ramta.client.service.settings.handler.JSONHandler;

import java.io.File;

public class Language {

    private final Logger logger = LoggerFactory.getLogger(Language.class);

    private final String name;
    private final String unicode;
    private final File languageFile;

    public Language(File languageFile) {
        this.languageFile = languageFile;
        String[] object = languageFile.getName().split("\\.");
        this.name = object[0];
        this.unicode = read("unicode");
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

    public Logger getLogger() {
        return logger;
    }

    private String read(String key) {
        JSONHandler handler = new JSONHandler(this.languageFile.getPath());
        return handler.getString(key);
    }

}
