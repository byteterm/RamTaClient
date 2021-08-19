package systems.tat.ramta.client.packets.in;

import io.netty.channel.Channel;
import systems.tat.ramta.client.lib.message.Message;
import systems.tat.ramta.client.service.socket.SocketClientHandlerService;

public class PacketInTest extends PacketIn {

    public PacketInTest(Message message, Channel channel, SocketClientHandlerService socketClientHandlerService) {
        super(message, channel, socketClientHandlerService);
        socketClientHandlerService.getController().blurPane.setVisible(true);
    }
}
