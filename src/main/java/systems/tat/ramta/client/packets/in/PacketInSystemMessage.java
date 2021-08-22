package systems.tat.ramta.client.packets.in;

import io.netty.channel.Channel;
import systems.tat.ramta.client.lib.message.Message;
import systems.tat.ramta.client.service.socket.SocketClientHandlerService;

public class PacketInSystemMessage extends PacketIn {

    public PacketInSystemMessage(Message message, Channel channel, SocketClientHandlerService socketClientHandlerService) {
        super(message, channel, socketClientHandlerService);
        handle();
    }

    private void handle() {
    }
}
