package systems.tat.ramta.client.packets.in;

import io.netty.channel.Channel;
import lombok.Getter;
import systems.tat.ramta.client.lib.message.Message;

@Getter
public abstract class PacketIn {

    private final Message message;
    private final Channel channel;

    public PacketIn(Message message, Channel channel){
        this.message = message;
        this.channel = channel;
    }
}
