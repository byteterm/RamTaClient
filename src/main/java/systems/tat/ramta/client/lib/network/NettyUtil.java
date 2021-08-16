package systems.tat.ramta.client.lib.network;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class NettyUtil {

    public static EventLoopGroup getEventLoopGroup() {
        return switch (getTransportType()) {
            case EPOLL -> new EpollEventLoopGroup();
            case NIO -> new NioEventLoopGroup();
        };
    }

    public static Class<? extends ServerSocketChannel> getServerSocketChannelClass() {
        return switch (getTransportType()) {
            case EPOLL -> EpollServerSocketChannel.class;
            case NIO -> NioServerSocketChannel.class;
        };
    }

    public static Class<? extends SocketChannel> getSocketChannelClass() {
        return switch (getTransportType()) {
            case EPOLL -> EpollSocketChannel.class;
            case NIO -> NioSocketChannel.class;
        };
    }

    public static NettyTransportType getTransportType() {
        if (epollAvailable()) return NettyTransportType.EPOLL;
        return NettyTransportType.NIO;
    }

    private static boolean epollAvailable() {
        return Epoll.isAvailable();
    }

    private static enum NettyTransportType {
        NIO, EPOLL
    }

}
