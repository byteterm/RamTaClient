package systems.tat.ramta.client.thread;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import systems.tat.ramta.client.lib.network.NettyUtil;
import systems.tat.ramta.client.service.socket.PipelineService;
import systems.tat.ramta.client.service.socket.SocketClientService;

@Component
public class SocketClientThread implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(SocketClientThread.class);

    private final PipelineService pipelineService;

    public SocketClientThread(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    @Override
    public void run() {

        while (true) {
            if (!connect()) {
                logger.info("Start ?");
                connect();
            }
        }
    }

    public boolean connect() {
        EventLoopGroup group = NettyUtil.getEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NettyUtil.getSocketChannelClass())
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(this.pipelineService);

        ChannelFuture f = null;

        try {
            f = b.connect("127.0.0.1", 7777).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.info("Test");
            return false;
        } finally {
            group.shutdownGracefully();
        }
        return true;
    }
}
