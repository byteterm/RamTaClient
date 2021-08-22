package systems.tat.ramta.client.models.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ChatBox {

    private final String direction;
    private AnchorPane pane;

    public ChatBox(String direction, ChatMember member, String text) {
        this.direction = direction;
        this.initialize(member, text);
    }

    private void initialize(ChatMember member, String text) {
        pane = new AnchorPane();
        Label label = new Label();
        label.setMinWidth(100);
        label.setMaxWidth(400);
        label.setText(text);

        if(direction.equals("left")) {
            label.setAlignment(Pos.TOP_LEFT);
        } else {
            label.setAlignment(Pos.TOP_RIGHT);
        }
        AnchorPane.setLeftAnchor(label, 100.0);

        label.getStyleClass().add("chat-message");

        //ImageView view = new ImageView();
        //Image image = new Image("file:///C:/Users/Daniel/Desktop/TestUmgebung/Test%20Test/frontend/displayed/default/assets/test.png");
        //view.setImage(image);
        //view.setFitWidth(64);
        //view.setFitHeight(64);
        //pane.getChildren().add(view);
        pane.getChildren().add(label);
    }

    public String getDirection() {
        return direction;
    }

    public AnchorPane getPane() {
        return pane;
    }
}
