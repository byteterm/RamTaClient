package systems.tat.ramta.client.service.gui;

import org.springframework.stereotype.Service;
import systems.tat.ramta.client.enums.DialogType;
import systems.tat.ramta.client.service.LanguageService;

@Service
public class DialogService {

    private final LanguageService service;

    public DialogService(LanguageService service) {
        this.service = service;
    }

    public void createDialog(DialogType type, String langKey) {

    }

    public LanguageService getService() {
        return service;
    }
}
