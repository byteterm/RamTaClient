package systems.tat.ramta.client.controller.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
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

    /*
    * General
    */
    public Label clientVersion;
    public Button exit;
    public Button minimize;
    public ChoiceBox<String> languageChoice;
    /*
    * Login Area
    */
    public Label signInLeftHeader;
    public Label signInText;
    public JFXButton signInSignUpBtn;
    public TextField signInTextInput;
    public PasswordField signInPasswordInput;
    public JFXButton signInBtn;
    public Label signInRightHeader;
    public JFXCheckBox remember;
    public Label passwordHelp;
    /*
    * Pages
    */
    public Pane signInPage;
    public Pane signInLeftPage;

    public Pane signUpPage;
    public Pane signUpLeftPage;

    /*
    * Register Area
    */
    public JFXButton signUpSignInBtn;
    public Label signUpText;
    public Label signUpLeftHeader;

    public JFXButton signUpBtn;
    public JFXCheckBox acceptBox;
    public TextField signUpTextInputEmail;
    public Label signUpRightHeader;
    public TextField signUpTextInputName;
    public PasswordField signUpPasswordInput;
    public Label rulesLink;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signUpBtn.setDisable(true);
        showSignIn();
        this.loadLanguages();
        this.loadChose();
        this.checkEula();
        System.out.println("Ready");
    }

    public void onChoseLang(ActionEvent event) {
        LanguageService service = RamTaClientFX.getLanguageService();
        String[] lang = languageChoice.getValue().split("[|]");
        lang[1] = lang[1].replaceAll(" ", "");
        service.selectLanguage(lang[1]);
        this.loadLanguages();
    }

    @FXML
    public void switchToSignUp(ActionEvent event) {
        showSignUp();
    }

    @FXML
    public void switchToSignIn(ActionEvent event) {
        showSignIn();
    }

    @FXML
    public void onCloseRequest(ActionEvent event) {
        System.exit( 0 );
    }

    @FXML
    public void onMinimize(ActionEvent event) {

    }

    private void loadLanguages() {
        registerLangObject(clientVersion, new ReflectKey("ver", "0.0.1-beta"));
        registerLangObject(signInLeftHeader);
        registerLangObject(signInText);
        registerLangObject(signInBtn);
        registerLangObject(signInRightHeader);
        registerLangObject(signInSignUpBtn);
        registerLangObject(passwordHelp);
        registerLangObject(remember);
        registerPromText(signInPasswordInput);
        registerPromText(signInTextInput);

        registerLangObject(signUpLeftHeader);
        registerLangObject(signUpText);
        registerLangObject(signUpSignInBtn);
        registerLangObject(signUpBtn);
        registerLangObject(acceptBox);
        registerLangObject(signUpRightHeader);
        registerLangObject(rulesLink);
        registerPromText(signUpTextInputEmail);
        registerPromText(signUpTextInputName);
        registerPromText(signUpPasswordInput);

    }

    private void checkEula() {
        acceptBox.setOnAction(event -> {
            if(acceptBox.isSelected()) {
                signUpBtn.setDisable(false);
            } else {
                signUpBtn.setDisable(true);
            }
        });
    }

    private void showSignUp() {
        signUpPage.setVisible(true);
        signUpLeftPage.setVisible(true);
        signInPage.setVisible(false);
        signInLeftPage.setVisible(false);
    }

    private void showSignIn() {
        signUpPage.setVisible(false);
        signUpLeftPage.setVisible(false);
        signInPage.setVisible(true);
        signInLeftPage.setVisible(true);
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

    private void registerPromText(TextInputControl control, ReflectKey... reflectKey) {
        LanguageService service = RamTaClientFX.getLanguageService();
        String text = service.getMessage("account", control.getId());
        if(reflectKey.length > 0) {
            for(ReflectKey keys : reflectKey) {
                text = text.replace(keys.getReflect(), keys.getKey());
            }
        }
        control.setPromptText(text);
    }

    private void loadChose() {
        LanguageService service = RamTaClientFX.getLanguageService();
        for(String string : service.getLanguages().keySet()) {
            languageChoice.getItems().add(service.getLanguages().get(string).getUnicode() + " | " + string);
        }
        languageChoice.setValue(service.getLanguages().get(service.getCurrentLanguages()).getUnicode() + " | "
        + service.getCurrentLanguages());
        languageChoice.setOnAction(this::onChoseLang);
    }

}
