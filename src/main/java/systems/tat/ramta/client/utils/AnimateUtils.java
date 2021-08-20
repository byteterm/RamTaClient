package systems.tat.ramta.client.utils;

import animatefx.animation.Bounce;
import javafx.scene.Node;
import javafx.util.Duration;

public class AnimateUtils {

    public static void Bounds(Node node, int count, double speed, String delay) {
        Bounce bounce = new Bounce(node);
        bounce.setCycleCount(count);
        bounce.setSpeed(speed);
        bounce.setDelay(Duration.valueOf(delay));
        bounce.play();
    }

}
