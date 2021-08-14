package systems.tat.ramta.client.service;

import systems.tat.ramta.client.RamTaClientFX;
import systems.tat.ramta.client.models.config.ConfigFile;
import systems.tat.ramta.client.service.settings.SettingService;
import systems.tat.ramta.client.service.settings.handler.JSONHandler;
import systems.tat.ramta.client.service.settings.handler.PropertiesHandler;
import systems.tat.ramta.client.utils.ResourcesUtils;

import java.io.File;
import java.util.*;

public class LanguageService {

    private final File RESOURCE_LOCATION = new File(ResourcesUtils.getExecutePath().getPath() + "/languages/");
    private final Map<String, File> languages = new HashMap<>();

    private String currentLanguages;

    public LanguageService() {
        this.loadLanguages();
    }

    public Map<String, File> getLanguages() {
        return languages;
    }

    public String getCurrentLanguages() {
        return currentLanguages;
    }

    public String getMessage(String sceneName, String languageKey) {
        if(currentLanguages == null) {
            return "Error: NLWS";
        }

        JSONHandler handler = new JSONHandler(languages.get(currentLanguages).getPath());
        return handler.getString(sceneName, languageKey);
    }

    public void selectLanguage(String language) {

    }

    private void saveLanguage() {

    }

    private void loadLanguages() {
        for(File file : Objects.requireNonNull(RESOURCE_LOCATION.listFiles())) {
            String[] array = file.getName().split("\\.");
            languages.put(array[0], file);
        }
        this.catchLanguage();
    }

    private void catchLanguage() {
        SettingService service = RamTaClientFX.getSettingService();
        if(!(service.getConfigs().containsKey("generalConf"))) {
            currentLanguages = "english";
            return;
        }

        ConfigFile config = service.getConfigs().get("generalConf");
        PropertiesHandler handler = (PropertiesHandler) config.getHandler();
        currentLanguages = handler.getString("usedLanguage");
    }
}
