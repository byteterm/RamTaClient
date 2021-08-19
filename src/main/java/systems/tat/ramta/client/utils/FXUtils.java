package systems.tat.ramta.client.utils;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class FXUtils {

    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void moveAbleWindow(Node headerParent, Stage stage) {
        headerParent.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        headerParent.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    public static List<Node> catchNode(Pane pane) {
        return paneNodes(pane, new ArrayList<>());
    }

    private static  <T extends Pane> List<Node> paneNodes(T parent, List<Node> nodes) {
        for(Node node : parent.getChildren()) {
            if(node instanceof  Pane) {
                paneNodes((Pane) node, nodes);
            } else {
                nodes.add(node);
            }
        }
        return nodes;
    }

}
