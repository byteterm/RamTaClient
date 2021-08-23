package systems.tat.ramta.client.packets.in;

import io.netty.channel.Channel;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import org.bson.types.Binary;
import systems.tat.ramta.client.lib.message.Message;
import systems.tat.ramta.client.service.socket.SocketClientHandlerService;

import java.io.InputStream;

public class PacketInSuccessLogin extends PacketIn {

    public PacketInSuccessLogin(Message message, Channel channel, SocketClientHandlerService socketClientHandlerService) {
        super(message, channel, socketClientHandlerService);

        handle();
    }

    private void handle() {
        getSocketClientHandlerService().getClient().setUsername(getMessage().get("Username").toString());
        if (getMessage().get("Image") != null) {
            //getSocketClientHandlerService().getClient().setImage((Binary) getMessage().get("Image"));
            // ToDo: getSocketClientHandlerService().getClientController().getYouImage().setFill(new ImagePattern(new Image()));
        }
        Platform.runLater(() -> {
            getSocketClientHandlerService().getAccountController().getDisplayService().displayScene("Client");
        });
    }
}
