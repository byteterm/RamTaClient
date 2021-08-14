package systems.tat.ramta.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import systems.tat.ramta.client.RamTaClientFX;
import systems.tat.ramta.client.models.gui.ReflectKey;
import systems.tat.ramta.client.service.LanguageService;

import java.net.URL;
import java.util.Locale;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loadLanguages();
    }

    @FXML
    public void onLoginTrigger(ActionEvent actionEvent) {
        System.out.println("Clicked!");
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
        String text = service.getMessage("account", node.getText().toUpperCase(Locale.ROOT));
        String reflected = text;
        if(reflectKey.length > 0) {
            for(ReflectKey keys : reflectKey) {
               reflected = text.replace(keys.getReflect(), keys.getKey());
            }
        }
        node.setText(reflected);
    }
}
