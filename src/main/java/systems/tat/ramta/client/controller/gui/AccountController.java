package systems.tat.ramta.client.controller.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import systems.tat.ramta.client.RamTaClientFX;
import systems.tat.ramta.client.models.gui.ReflectKey;
import systems.tat.ramta.client.service.LanguageService;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("/fxml/account.fxml")
public class AccountController implements Initializable {

    public Label accountHeadline;

    public Label userinHeadline;
    public TextField username;

    public Label passHeadline;
    public PasswordField password;

    public Button loginBtn;
    public Label clientVersion;

    public ChoiceBox<String> languageSet;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loadLanguages();
        this.loadChose();
    }

    @FXML
    public void onLoginTrigger(ActionEvent actionEvent) {
        System.out.println("Clicked!");
        //ToDo: This line will be removed and called when the user can enter the client...
        StageInitializer.getDisplayService().displayScene("client");
    }

    public void onChoseLang(ActionEvent event) {
        LanguageService service = RamTaClientFX.getLanguageService();
        String language = languageSet.getValue();
        System.out.println(language);
        service.selectLanguage(language);
        this.loadLanguages();
    }

    private void loadLanguages() {
        registerLangObject(accountHeadline);
        registerLangObject(userinHeadline);
        registerLangObject(passHeadline);
        registerLangObject(loginBtn);
        registerLangObject(clientVersion, new ReflectKey("ver", "0.0.1-beta"));
    }

    private void registerLangObject(Labeled node, ReflectKey... reflectKey) {
        LanguageService service = RamTaClientFX.getLanguageService();
        String text = service.getMessage("account", node.getId());
        if(reflectKey.length > 0) {
            for(ReflectKey keys : reflectKey) {
               text = text.replace(keys.getReflect(), keys.getKey());
            }
        }
        node.setText(text);
    }

    private void loadChose() {
        LanguageService service = RamTaClientFX.getLanguageService();
        for(String string : service.getLanguages().keySet()) {
            languageSet.getItems().add(string);
        }
        languageSet.setValue(service.getCurrentLanguages());
        languageSet.setOnAction(this::onChoseLang);
    }

}
