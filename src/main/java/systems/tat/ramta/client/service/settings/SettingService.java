package systems.tat.ramta.client.service.settings;

import org.springframework.stereotype.Service;
import systems.tat.ramta.client.models.config.ConfigFile;
import systems.tat.ramta.client.utils.ResourcesUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class SettingService {

    private final File RESOURCE_LOCATION = new File(ResourcesUtils.getPath("system/"));
    private final Map<String, ConfigFile> configs = new HashMap<>();

    public SettingService() {
        this.loadConfigurations();
    }

    private void loadConfigurations() {
        for(File file : Objects.requireNonNull(RESOURCE_LOCATION.listFiles())) {
            ConfigFile config = new ConfigFile(file.getName(), file.getPath());
            if(!(configs.containsKey(config.getName()))) {
                configs.put(config.getName(), config);
            }
        }
    }

    public void addConfiguration(ConfigFile config) {
        if(!(configs.containsKey(config.getName()))) {
            configs.put(config.getName(), config);
        }
    }

    public void removeConfiguration(String name) {
        if(!(configs.containsKey(name))) {
            configs.remove(name);
        }
    }

    public Map<String, ConfigFile> getConfigs() {
        return configs;
    }
}
