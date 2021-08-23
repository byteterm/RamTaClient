package systems.tat.ramta.client.controller.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import lombok.Data;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import systems.tat.ramta.client.enums.ChatSender;
import systems.tat.ramta.client.models.gui.ChatMember;
import systems.tat.ramta.client.service.gui.DisplayService;
import systems.tat.ramta.client.service.system.ChatService;
import systems.tat.ramta.client.utils.FXUtils;

import javax.swing.text.html.ListView;

@Component
@Data
@FxmlView("/fxml/client.fxml")
public class ClientController {

    /* General area */
    public AnchorPane header;
    public Button exit;
    public Button maximize;
    public Button minimize;
    /* Chat area */
    public AnchorPane chatPane;
    public TextField chatInput;
    public ScrollBar chatScroll;
    public VBox chatView;
    /* User header */
    public Circle youImage;

    private final DisplayService displayService;
    @Lazy
    private final ChatService chatService;

    @FXML
    public void initialize() {
        FXUtils.moveAbleWindow(header, displayService.getStage());
        youImage.setFill(new ImagePattern(new Image("https://1.bp.blogspot.com/-bjvUSn0Gwro/Xwi-faIkn5I/AAAAAAABoYA/mll1pCwHpPIVJXbpwrJIaZ8E8ZtIqPq6wCLcBGAsYHQ/s1600/4655.jpg")));
    }

    @FXML
    public void onClose(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void onMaximize(ActionEvent event) {
        if(displayService.getStage().isMaximized()) {
            displayService.getStage().setMaximized(false);
            return;
        }
        displayService.getStage().setMaximized(true);
    }

    @FXML
    public void onMinimize(ActionEvent event) {
        displayService.getStage().setIconified(true);
    }

    @FXML
    public void onSpelling(ActionEvent event) {
    }

    @FXML
    public void sendChatMessage(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)) {
            ChatMember member = new ChatMember("Exepta", "", ChatSender.YOU_SELF);
            chatService.displayChatMessage(chatView, member, chatInput.getText());
            chatInput.setText("");
        }

        if(event.getCode().equals(KeyCode.F1)) {
            ChatMember member = new ChatMember("Niklas", "", ChatSender.TARGET);
            chatService.displayChatMessage(chatView, member, chatInput.getText());
            chatInput.setText("");
        }
    }
}
