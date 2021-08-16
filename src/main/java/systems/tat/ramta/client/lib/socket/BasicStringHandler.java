package systems.tat.ramta.client.lib.socket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import systems.tat.ramta.client.lib.message.Message;

import java.util.HashMap;
import java.util.Map;

public abstract class BasicStringHandler extends SimpleChannelInboundHandler<String> {

    private final Map<Channel, Integer> open;
    private final Map<Channel, StringBuilder> parsed;
    private final Map<Channel, Boolean> isString;

    public BasicStringHandler() {
        this.open = new HashMap<>();
        this.parsed = new HashMap<>();
        this.isString = new HashMap<>();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

    }

    public void read(Channel channel, String message) {
        for (String c : message.split("")) {
            getParsed(channel).append(c);

            if (c.equals("\"")
                    & (getParsed(channel).length() < 2
                    | ! Character.toString(getParsed(channel).charAt(getParsed(channel).length() -2)).equals("\\")))
            setIsString(channel, !isString(channel));

            if (isString(channel))
                continue;

            if (c.equals("{"))
                open.put(channel, getOpen(channel) + 1);

            if (c.equals("}")) {
                open.put(channel, getOpen(channel) -1);

                if (getOpen(channel) == 0) {
                    try {
                        String parsed = getParsed(channel).toString();
                        handleMessage(Message.createFromJsonString(parsed), parsed, channel);
                    } catch (Exception ex) {
                        System.err.println("Error while parsing JSON message: " + getParsed(channel));
                        ex.printStackTrace();
                    }
                    parsed.put(channel, new StringBuilder());
                }
            }
        }
    }

    public abstract void handleMessage(Message message, String originalMessage, Channel channel);

    public int getOpen(Channel channel) {
        open.putIfAbsent(channel, 0);
        return open.get(channel);
    }

    private StringBuilder getParsed(Channel channel) {
        parsed.putIfAbsent(channel, new StringBuilder());
        return parsed.get(channel);
    }

    private boolean isString(Channel channel) {
        return this.isString.getOrDefault(channel, false);
    }

    private void setIsString(Channel channel, boolean isString) {
        this.isString.put(channel, isString);
    }

    public void closeChannel(Channel channel) {
        channel.close();
        open.remove(channel);
        parsed.remove(channel);
        isString.remove(channel);
    }
}
