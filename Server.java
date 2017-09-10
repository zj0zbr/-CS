package until.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class Server {
    public void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                             //server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
//                            ch.pipeline().addLast(new HttpResponseEncoder());
                            // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
//                            ch.pipeline().addLast(new HttpRequestDecoder());
//                            ch.pipeline().addLast(new HttpObjectAggregator(65535));
                            ch.pipeline().addLast(new ChunkedWriteHandler());
//                            ch.pipeline().addLast((io.netty.channel.ChannelHandler) new ChannelHandler());
                            ch.pipeline().addLast(new HttpInboundHandler());
                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            Channel channel = b.bind(port).sync().channel();
            //绑定端口（实际上是创建serversocketchannnel，并注册到eventloop上），同步等待完成，返回相应channel
//            ChannelFuture f = b.bind(port).sync();

            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

//    public static void main(String[] args) throws Exception {
//        Server server = new Server();
//        server.start(8000);
//    }

}
//            ChannelGroup group = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
//            System.out.println("+++++++++++++++++++++++++++++++++");
//            System.out.println(group);
//            System.out.println("+++++++++++++++++++++++++++++++++");