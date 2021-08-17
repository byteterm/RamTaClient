package systems.tat.ramta.client.service;

import systems.tat.ramta.client.RamTaClientFX;
import systems.tat.ramta.client.models.config.ConfigFile;
import systems.tat.ramta.client.models.lang.Language;
import systems.tat.ramta.client.service.settings.SettingService;
import systems.tat.ramta.client.service.settings.handler.JSONHandler;
import systems.tat.ramta.client.service.settings.handler.PropertiesHandler;
import systems.tat.ramta.client.utils.ResourcesUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LanguageService {

    private final File RESOURCE_LOCATION = new File(ResourcesUtils.getPath("languages/"));
    private final Map<String, Language> languages = new HashMap<>();

    private String currentLanguages;

    public LanguageService() {
        this.loadLanguages();
    }

    public Map<String, Language> getLanguages() {
        return languages;
    }

    public String getCurrentLanguages() {
        return currentLanguages;
    }

    public String getMessage(String sceneName, String languageKey) {
        if(currentLanguages == null) {
            return "Error: NLWS";
        }

        JSONHandler handler = new JSONHandler(languages.get(currentLanguages).getLanguageFile().getPath());
        byte[] original = handler.getString(sceneName, languageKey).getBytes();
        return new String(original, StandardCharsets.UTF_8);
    }

    public void selectLanguage(String language) {
        currentLanguages = language;
        this.saveLanguage();
    }

    private void saveLanguage() {

    }

    private void loadLanguages() {
        for(File file : Objects.requireNonNull(RESOURCE_LOCATION.listFiles())) {
            String[] array = file.getName().split("\\.");
            Language language = new Language(file);
            languages.put(array[0], language);
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
