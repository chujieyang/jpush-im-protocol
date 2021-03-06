package com.jpush.im.protocal.common;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.google.protobuf.ExtensionRegistry;
import com.jpush.im.protobuf.Im;
import com.jpush.im.protocal.decoder.ImProtocalServerDecoder;
import com.jpush.im.protocal.decoder.ImProtocalServerEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

public class NettyTcpServer {
	private static Logger log = (Logger) LoggerFactory.getLogger(NettyTcpServer.class);
	
	private int port;

   public NettyTcpServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); 
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try {
            ServerBootstrap b = new ServerBootstrap(); 
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) 
             .option(ChannelOption.SO_BACKLOG, 128)
             .childOption(ChannelOption.SO_KEEPALIVE, true)
             .childHandler(new ChannelInitializer<SocketChannel>() { 
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(10, 10, 0))
                     				.addLast(new ImProtocalServerEncoder())
                     				.addLast(new ImProtocalServerDecoder())
                     				.addLast(new NettyTcpServerHandler());
                 	 }
             	}); 

            ChannelFuture f = b.bind(port).sync(); 
            log.info("server is starting......");
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
    		new NettyTcpServer(7000).run();
    }
}
