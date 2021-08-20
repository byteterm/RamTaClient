package systems.tat.ramta.client.controller.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import net.rgielen.fxweaver.core.FxmlView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import systems.tat.ramta.client.models.Client;
import systems.tat.ramta.client.models.gui.ReflectKey;
import systems.tat.ramta.client.packets.out.PacketOutRegisterAccount;
import systems.tat.ramta.client.service.LanguageService;
import systems.tat.ramta.client.service.gui.DisplayService;
import systems.tat.ramta.client.service.socket.SocketClientHandlerService;
import systems.tat.ramta.client.utils.FXUtils;

import java.util.List;

@Component
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

    private List<Node> nodes;

    private final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final Client client;
    private final LanguageService languageService;
    private final DisplayService displayService;
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
