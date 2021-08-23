package systems.tat.ramta.client.packets.in;

import io.netty.channel.Channel;
import systems.tat.ramta.client.lib.message.Message;
import systems.tat.ramta.client.service.socket.SocketClientHandlerService;

public class PacketInSuccessLogin extends PacketIn {
    public PacketInSuccessLogin(Message message, Channel channel, SocketClientHandlerService socketClientHandlerService) {
        super(message, channel, socketClientHandlerService);
    }

    private void handle() {
        getSocketClientHandlerService().getClient().setUsername(getMessage().get("Username").toString());
        getSocketClientHandlerService().getClient().setEmail(getMessage().get("Image").toString());

        getSocketClientHandlerService().getAccountController().getDisplayService().displayScene("Client");
    }
}
