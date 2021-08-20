package systems.tat.ramta.client.service.system;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Service;
import systems.tat.ramta.client.models.gui.ChatMember;

@Service
public class ChatService {

    /* This is an example for the chat message box Todo: remove that and make it cleaner... */
    public void displayChatMessage(Pane chatPane, ChatMember member, String message) {
        HBox block = new HBox();
        block.setMinHeight(100);
        block.getStyleClass().add("chat-box");

        VBox memberInfo = new VBox();
        memberInfo.setMinWidth(150);
        memberInfo.setAlignment(Pos.CENTER);
        Label name = new Label();
        name.setText(member.getMemberName());
        Label id = new Label();
        id.setText(member.getMemberID());
        memberInfo.getChildren().add(name);
        memberInfo.getChildren().add(id);

        Label label = new Label();
        label.setPrefWidth(400);
        label.setMaxWidth(400);
        label.setText(message);

        block.getChildren().add(memberInfo);
        block.getChildren().add(label);
        chatPane.getChildren().add(block);
    }

}
