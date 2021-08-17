package systems.tat.ramta.client.service.socket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import systems.tat.ramta.client.enums.ClientStatus;
import systems.tat.ramta.client.lib.message.Message;
import systems.tat.ramta.client.packets.out.PacketOutHandshake;

@EqualsAndHashCode(callSuper = false)
@Service
@ChannelHandler.Sharable
@Data
public class SocketClientHandlerService extends ChannelInboundHandlerAdapter {

    private Channel channel;
    private final Logger logger = LoggerFactory.getLogger(SocketClientHandlerService.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        setChannel(ctx.channel());
        logger.info("Connected to Server");
        new PacketOutHandshake(this, ClientStatus.LoginIn);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    public void sendMessage(Message message) {
        sendMessage(message.toJson());
    }

    public void sendMessage(String message) {
        if (channel != null
                && channel.isActive()) {
            logger.info("Message Send");
            channel.writeAndFlush(message);
        } else {
            logger.info("Message not send");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
