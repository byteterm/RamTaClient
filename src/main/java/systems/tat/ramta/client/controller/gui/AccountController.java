package systems.tat.ramta.client.controller.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import systems.tat.ramta.client.RamTaClientFX;
import systems.tat.ramta.client.models.Client;
import systems.tat.ramta.client.models.gui.ReflectKey;
import systems.tat.ramta.client.service.LanguageService;

@Component
@FxmlView("/fxml/account.fxml")
public class AccountController {

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


    private final Client client;
    private final LanguageService languageService;

    public AccountController(Client client, LanguageService languageService) {
        this.client = client;
        this.languageService = languageService;
    }

    @FXML
    public void initialize() {
        signUpBtn.setDisable(true);
        showSignIn();
        this.loadLanguages();
        this.loadChose();
        this.checkEula();
    }

    public void onChoseLang(ActionEvent event) {
        String[] lang = languageChoice.getValue().split("[|]");
        lang[1] = lang[1].replaceAll(" ", "");
        languageService.selectLanguage(lang[1]);
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

    @FXML
    public void onRegister(ActionEvent actionEvent) {
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
        String text = languageService.getMessage("account", node.getId());
        if(reflectKey.length > 0) {
            for(ReflectKey keys : reflectKey) {
               text = text.replace(keys.getReflect(), keys.getKey());
            }
        }
        node.setText(text);
    }

    private void registerPromText(TextInputControl control, ReflectKey... reflectKey) {
        String text = languageService.getMessage("account", control.getId());
        if(reflectKey.length > 0) {
            for(ReflectKey keys : reflectKey) {
                text = text.replace(keys.getReflect(), keys.getKey());
            }
        }
        control.setPromptText(text);
    }

    private void loadChose() {
        for(String string : languageService.getLanguages().keySet()) {
            languageChoice.getItems().add(languageService.getLanguages().get(string).getUnicode() + " | " + string);
        }
        languageChoice.setValue(languageService.getLanguages().get(languageService.getCurrentLanguages()).getUnicode() + " | "
        + languageService.getCurrentLanguages());
        languageChoice.setOnAction(this::onChoseLang);
    }
}
