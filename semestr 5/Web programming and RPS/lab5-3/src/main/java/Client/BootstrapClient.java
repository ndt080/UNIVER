package Client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

public class BootstrapClient {

    private Logger theLogger = LogManager.getLogger(BootstrapClient.class);

    public static void main(String args[]) throws IOException {
        BootstrapClient client = new BootstrapClient();
        client.bootstrap();
    }

    public void bootstrap() throws IOException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(
                                new StringEncoder(),
                                new StringDecoder(),
                                new ClientHandler());
                    }
                });
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost", 9999));
        Channel channel = future.channel();
        future.addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                theLogger.info("Connection established");
            } else {
                theLogger.info("Connection attempt failed");
                channelFuture.cause().printStackTrace();
            }
        });
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            channel.write(bufferedReader.readLine() + "\r\n");
            channel.flush();
        }
    }
}

class ClientHandler extends SimpleChannelInboundHandler<String> {
    private Logger theLogger = LogManager.getLogger(ClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        theLogger.info(s);
    }
}
