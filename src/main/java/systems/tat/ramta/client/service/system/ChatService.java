package systems.tat.ramta.client.service.system;

import javafx.geometry.NodeOrientation;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Service;
import systems.tat.ramta.client.enums.ChatSender;
import systems.tat.ramta.client.models.gui.ChatBox;
import systems.tat.ramta.client.models.gui.ChatMember;

@Service
public class ChatService {

    /* This is an example for the chat message box Todo: remove that and make it cleaner... */
    public void displayChatMessage(Pane chatPane, ChatMember member, String message) {
        ChatBox box = null;
        if(member.getSender().equals(ChatSender.YOU_SELF)) {
            box = new ChatBox("left", member, message);
            box.getPane().setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        } else {
            box = new ChatBox("right", member, message);
            box.getPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        }
        chatPane.getChildren().add(box.getPane());
    }

}
