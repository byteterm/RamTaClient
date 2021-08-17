package systems.tat.ramta.client.service.socket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Service;
import systems.tat.ramta.client.utils.ResourcesUtils;

import javax.net.ssl.SSLException;
import java.io.File;

@Service
public class PipelineService extends ChannelInitializer<Channel> {

    private final SslContext sslContext;
    private final StringHandlerService clientStringHandler;
    private final SocketClientHandlerService socketClientHandler;

    public PipelineService(StringHandlerService clientStringHandler, SocketClientHandlerService socketClientHandler) throws SSLException {
        this.clientStringHandler = clientStringHandler;
        this.socketClientHandler = socketClientHandler;
        this.sslContext = SslContextBuilder.forClient().trustManager(
                new File(ResourcesUtils.getExecutePath().getPath()+ "/cert/cert.pem")).build();
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(socketClientHandler);
        // Add ssl
        //pipeline.addLast(sslContext.newHandler(ch.alloc(), "client.ramta.tat.systems", 7777));
        //pipeline.addLast(sslContext.newHandler(ch.alloc()));

        // text line codec
        //pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));

        // Business logic
        pipeline.addLast("handler", clientStringHandler);
    }
}
