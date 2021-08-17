package systems.tat.ramta.client.packets.in;

import io.netty.channel.Channel;
import lombok.Getter;
import systems.tat.ramta.client.lib.message.Message;
import systems.tat.ramta.client.service.socket.SocketClientHandlerService;

@Getter
public abstract class PacketIn {

    private final Message message;
    private final Channel channel;
    private final SocketClientHandlerService socketClientHandlerService;

    public PacketIn(Message message, Channel channel, SocketClientHandlerService socketClientHandlerService){
        this.message = message;
        this.channel = channel;
        this.socketClientHandlerService = socketClientHandlerService;
    }
}
