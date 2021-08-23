package systems.tat.ramta.client.models.gui;

import javafx.scene.image.Image;
import lombok.Data;
import lombok.Getter;
import systems.tat.ramta.client.enums.ChatSender;

@Data
@Getter
public class ChatMember {

    private final String memberName;
    private final String memberID;
    private final ChatSender sender;
    private final Image image;

}
