package systems.tat.ramta.client.controller.gui;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Data;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import systems.tat.ramta.client.models.gui.ChatMember;
import systems.tat.ramta.client.service.gui.DisplayService;
import systems.tat.ramta.client.service.system.ChatService;
import systems.tat.ramta.client.utils.FXUtils;

@Component
@Data
@FxmlView("/fxml/client.fxml")
public class ClientController {

    /*
    * General Scene elements
    */
    public Pane header;
    public Button exit;
    public Button resize;
    public Button minimize;

    /*
    * Chat pane
    */
    public TextField chatMessage;
    public JFXButton sendMessageBtn;
    public VBox chatPane;
    public ScrollPane scroller;


    private final DisplayService displayService;
    @Lazy
    private final ChatService chatService;

    @FXML
    public void initialize() {
        FXUtils.moveAbleWindow(header, displayService.getStage());
    }

    @FXML
    public void onClose(ActionEvent event) {
        System.exit( 0 );
    }

    @FXML
    public void onMinimize(ActionEvent event) {
        displayService.getStage().setIconified(true);
    }

    @FXML
    public void onResize(ActionEvent event) {
        //Todo: change the size of the window to max and oldSize
    }

    @FXML
    public void sendMessage(ActionEvent event) {
        String message = chatMessage.getText();
        ChatMember member = new ChatMember("Exepta", "#1996");
        chatService.displayChatMessage(chatPane, member, message);
        chatMessage.setText("");
        scroller.setVvalue(scroller.getVmax());
    }
}
