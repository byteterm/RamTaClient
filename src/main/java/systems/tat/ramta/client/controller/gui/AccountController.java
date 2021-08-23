package systems.tat.ramta.client.controller.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import systems.tat.ramta.client.models.Client;
import systems.tat.ramta.client.models.gui.ReflectKey;
import systems.tat.ramta.client.packets.out.PacketOutLogin;
import systems.tat.ramta.client.packets.out.PacketOutRegisterAccount;
import systems.tat.ramta.client.service.LanguageService;
import systems.tat.ramta.client.service.gui.DisplayService;
import systems.tat.ramta.client.service.socket.SocketClientHandlerService;
import systems.tat.ramta.client.utils.AnimateUtils;
import systems.tat.ramta.client.utils.FXUtils;

import java.util.List;

@Component
@Getter
@FxmlView("/fxml/account.fxml")
public class AccountController {

    /*
    * General
    */
    public Pane header;
    public AnchorPane blurPane;
    public AnchorPane mainFrame;
    public Label clientVersion;
    public Button exit;
    public Button minimize;
    public ChoiceBox<String> languageChoice;
    /*
    * Login Area
    */
    public JFXButton signInSignUpBtn;
    public TextField signInTextInput;
    public PasswordField signInPasswordInput;
    public JFXButton signInBtn;
    public JFXCheckBox remember;
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

    public JFXButton signUpBtn;
    public JFXCheckBox acceptBox;
    public TextField signUpTextInputEmail;
    public TextField signUpTextInputName;
    public PasswordField signUpPasswordInput;

    public Circle loadingIcon01;
    public Circle loadingIcon02;
    public Circle loadingIcon03;

    private List<Node> nodes;

    private final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final Client client;
    private final LanguageService languageService;
    public final DisplayService displayService;
    private final SocketClientHandlerService clientHandlerService;

    public AccountController(
            Client client,
            LanguageService languageService,
            DisplayService displayService,@Lazy SocketClientHandlerService clientHandlerService) {
        this.client = client;
        this.languageService = languageService;
        this.displayService = displayService;
        this.clientHandlerService = clientHandlerService;
    }

    @FXML
    public void initialize() {
        nodes = FXUtils.catchNode(mainFrame);
        signUpBtn.setDisable(true);
        showSignIn();
        this.loadLanguages();
        this.loadChose();
        this.checkEula();
        FXUtils.moveAbleWindow(header, displayService.getStage());
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
        displayService.getStage().setIconified(true);
    }

    @FXML
    public void onRegister(ActionEvent actionEvent) {
        if(FXUtils.checkNull(signUpTextInputName.getText())) {
            client.setUsername(signUpTextInputName.getText());
        }
        if(FXUtils.checkNull(signUpTextInputEmail.getText())) {
            client.setEmail(signUpTextInputEmail.getText());
        }
        if(FXUtils.checkNull(signUpPasswordInput.getText())) {
            client.setPassword(signUpPasswordInput.getText());
        }

        if(client.getUsername() != null & client.getEmail() != null & client.getPassword() != null) {
            new PacketOutRegisterAccount(clientHandlerService, client);
        } else {
            logger.warn("Can't send packet when the input is null!");
        }
    }

    @FXML
    public void onLogin(ActionEvent event) {
        if(FXUtils.checkNull(signInPasswordInput.getText())
                & FXUtils.checkNull(signInTextInput.getText())) {

            Client client = new Client();
            client.setPassword(signInPasswordInput.getText());
            client.setEmail(signInTextInput.getText());

            new PacketOutLogin(clientHandlerService, client);
        }
    }

    public void playLoadingScreen() {
        if(blurPane.isVisible()) {
            AnimateUtils.Bounds(loadingIcon01, Timeline.INDEFINITE, 0.45, "500ms");
            AnimateUtils.Bounds(loadingIcon02, Timeline.INDEFINITE, 0.45,"1000ms");
            AnimateUtils.Bounds(loadingIcon03, Timeline.INDEFINITE, 0.45,"1100ms");
        }
    }

    private void loadLanguages() {
        for(Node node : nodes) {
            if(node.getId() != null) {
                if(!node.getId().equals("exit")) {
                    if(!node.getId().equals("minimize")) {
                        registerLangObject(node);
                    }
                }
            }
        }
        registerLangObject(clientVersion, new ReflectKey("ver", "0.0.1-beta"));
    }

    private void checkEula() {
        acceptBox.setOnAction(event -> {
            signUpBtn.setDisable(!acceptBox.isSelected());
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

    private void registerLangObject(Node node, ReflectKey... reflectKey) {
        String text = languageService.getMessage("account", node.getId());
        if(reflectKey.length > 0) {
            for(ReflectKey keys : reflectKey) {
               text = text.replace(keys.getReflect(), keys.getKey());
            }
        }
        if(node instanceof Labeled labeled) {
            labeled.setText(text);
        } else if(node instanceof TextInputControl control) {
            control.setPromptText(text);
        }
    }

    private void loadChose() {
        for(String string : languageService.getLanguages().keySet()) {
            languageChoice.getItems().add("   " + languageService.getLanguages().get(string).getUnicode() + " | " + string);
        }
        languageChoice.setValue("   " + languageService.getLanguages().get(languageService.getCurrentLanguages()).getUnicode() + " | "
        + languageService.getCurrentLanguages());
        languageChoice.setOnAction(this::onChoseLang);
    }

}
