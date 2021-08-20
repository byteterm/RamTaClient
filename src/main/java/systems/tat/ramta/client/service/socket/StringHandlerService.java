package systems.tat.ramta.client.service.socket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import systems.tat.ramta.client.lib.message.Message;
import systems.tat.ramta.client.lib.socket.BasicStringHandler;

import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@ChannelHandler.Sharable
public class StringHandlerService extends BasicStringHandler {

    private final SocketClientHandlerService socketClientHandlerService;

    Logger logger = LoggerFactory.getLogger(StringHandlerService.class);

    public StringHandlerService(SocketClientHandlerService socketClientHandlerService) {
        this.socketClientHandlerService = socketClientHandlerService;
    }

    @Override
    public void handleMessage(Message message, String originalMessage, Channel channel) {
        logger.info("Incoming packed named " + message.getType());
        try {
            Class<?> packetClass = Class.forName("systems.tat.ramta.client.packets.in.PacketIn" + message.getType());
            Constructor<?> constructor = packetClass.getConstructor(Message.class, Channel.class, SocketClientHandlerService.class);

            constructor.newInstance(message, channel, socketClientHandlerService);
        } catch (Exception ex) {
            logger.info("No Handler for packet " + message.getType() + " found");
        }
    }

    private byte[] stringToByteArray(String input) {
        return Base64.getDecoder().decode(input.getBytes(StandardCharsets.UTF_8));
    }
}
