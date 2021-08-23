package systems.tat.ramta.client.packets.in;

import io.netty.channel.Channel;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import systems.tat.ramta.client.lib.message.Message;
import systems.tat.ramta.client.service.socket.SocketClientHandlerService;

import java.io.ByteArrayInputStream;
import java.util.Base64;

public class PacketInSuccessLogin extends PacketIn {

    public PacketInSuccessLogin(Message message, Channel channel, SocketClientHandlerService socketClientHandlerService) {
        super(message, channel, socketClientHandlerService);

        handle();
    }

    private void handle() {
        getSocketClientHandlerService().getClient().setUsername(getMessage().get("Username").toString());
        getSocketClientHandlerService().getClient().setNameId(getMessage().get("NameId").toString());
        getSocketClientHandlerService().getClient().setUuid(getMessage().get("UUID").toString());

        if (getMessage().get("Image") != null) {
            getSocketClientHandlerService().getClient().setImage(getMessage().get("Image").toString());
            byte[] decodedBytes = Base64.getDecoder().decode(getSocketClientHandlerService().getClient().getImage());

            Platform.runLater(() -> {
                getSocketClientHandlerService().getAccountController().getClientController().getYouImage().setFill(new ImagePattern(new Image(new ByteArrayInputStream(decodedBytes))));
            });
        }
        Platform.runLater(() -> {
            //ToDo: add avatar
            //getSocketClientHandlerService().getAccountController().getClientController().
            getSocketClientHandlerService().getAccountController().getDisplayService().displayScene("Client");
        });
    }
}
