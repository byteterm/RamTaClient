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
import systems.tat.ramta.client.models.Client;
import systems.tat.ramta.client.packets.out.PacketOutHandshake;

@EqualsAndHashCode(callSuper = false)
@Service
@ChannelHandler.Sharable
@Data
public class SocketClientHandlerService extends ChannelInboundHandlerAdapter {

    private Channel channel;
    private final Client client;
    private final Logger logger = LoggerFactory.getLogger(SocketClientHandlerService.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        setChannel(ctx.channel());
        new PacketOutHandshake(this, ClientStatus.LoginIn);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    public void sendMessage(Message message) {
        sendMessage(message.toJson());

        while (channel.isActive()) {
           logger.info(client.getUsername());
           try {
               Thread.sleep(2000);
           } catch (Exception ignored) {}
        }
    }

    public void sendMessage(String message) {
        if (channel != null
                && channel.isActive()) {
            channel.writeAndFlush(message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
