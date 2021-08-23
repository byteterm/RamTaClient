package systems.tat.ramta.client.packets.in;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import systems.tat.ramta.client.lib.message.Message;
import systems.tat.ramta.client.service.socket.SocketClientHandlerService;

public class PacketInSystemMessage extends PacketIn {

    private final Logger logger = LoggerFactory.getLogger(PacketInSystemMessage.class);

    public PacketInSystemMessage(Message message, Channel channel, SocketClientHandlerService socketClientHandlerService) {
        super(message, channel, socketClientHandlerService);
        handle();
    }

    private void handle() {
        logger.info(getMessage().getType() + " -> " + getMessage().get("MessageType") + " : " + getMessage().get("Message"));
    }
}
