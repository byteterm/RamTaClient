package systems.tat.ramta.client.controller.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import systems.tat.ramta.client.service.socket.SocketClientHandlerService;
import systems.tat.ramta.client.service.system.ChatService;
import systems.tat.ramta.client.utils.FXUtils;

import javax.swing.text.html.ListView;
import java.io.ByteArrayInputStream;
import java.util.Base64;

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
    public Label userName;

    private final DisplayService displayService;
    private final SocketClientHandlerService clientHandlerService;
    @Lazy
    private final ChatService chatService;

    @FXML
    public void initialize() {
        FXUtils.moveAbleWindow(header, displayService.getStage());
        youImage.setFill(new ImagePattern(new Image("https://i.ibb.co/xjy0RkY/Download-1.png")));
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
            ChatMember member = new ChatMember("Exepta", "", ChatSender.YOU_SELF, new Image("https://1.bp.blogspot.com/-bjvUSn0Gwro/Xwi-faIkn5I/AAAAAAABoYA/mll1pCwHpPIVJXbpwrJIaZ8E8ZtIqPq6wCLcBGAsYHQ/s1600/4655.jpg"));
            chatService.displayChatMessage(chatView, member, chatInput.getText());
            chatInput.setText("");
        }

        if(event.getCode().equals(KeyCode.F1)) {
            ChatMember member = new ChatMember("Niklas", "", ChatSender.TARGET, new Image("https://scontent.fdtm2-2.fna.fbcdn.net/v/t1.6435-9/180978949_314228950059549_1005358403722529104_n.jpg?_nc_cat=105&ccb=1-5&_nc_sid=09cbfe&_nc_ohc=S_kDNy6y7GAAX9Ji0g4&_nc_ht=scontent.fdtm2-2.fna&oh=a355fbc407e1e3295c555f8c71f6c10a&oe=6147DF8E"));
            chatService.displayChatMessage(chatView, member, chatInput.getText());
            chatInput.setText("");
        }
    }
}
