package me.kuuds.codec.example.modbus;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;
import lombok.extern.slf4j.Slf4j;
import me.kuuds.codec.common.MetricsHandler;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutionException;

@Slf4j
public class ModbusTcpServer {

    public static void main(String[] args) throws InterruptedException, ExecutionException, CertificateException, SSLException {

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.option(NioChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.childOption(NioChannelOption.TCP_NODELAY, true);
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));

        //thread
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(0, new DefaultThreadFactory("boss"));
        NioEventLoopGroup workGroup = new NioEventLoopGroup(0, new DefaultThreadFactory("worker"));
        UnorderedThreadPoolEventExecutor businessGroup = new UnorderedThreadPoolEventExecutor(10, new DefaultThreadFactory("business"));
        NioEventLoopGroup eventLoopGroupForTrafficShaping = new NioEventLoopGroup(0, new DefaultThreadFactory("TS"));

        try {

            serverBootstrap.group(bossGroup, workGroup);
            //metrics

            MeterRegistry meterRegistry = new SimpleMeterRegistry();
            MetricsHandler metricsHandler = new MetricsHandler(meterRegistry);

            ChannelInitializer<SocketChannel> channelInitializer = new ModbusChannelInitializer(metricsHandler);

            serverBootstrap.childHandler(channelInitializer);

            ChannelFuture channelFuture = serverBootstrap.bind(8090).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            businessGroup.shutdownGracefully();
            eventLoopGroupForTrafficShaping.shutdownGracefully();
        }

    }

}
