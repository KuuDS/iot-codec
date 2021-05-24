package me.kuuds.codec.example;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.flush.FlushConsolidationHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;
import lombok.extern.slf4j.Slf4j;
import me.kuuds.codec.common.MetricsHandler;
import me.kuuds.codec.common.ServerIdleCheckHandler;

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

            MeterRegistry meterRegistry = new SimpleMeterRegistry();
            //metrics
            MetricsHandler metricsHandler = new MetricsHandler(meterRegistry);
            //auth

            //log
            LoggingHandler debugLogHandler = new LoggingHandler(LogLevel.DEBUG);
            LoggingHandler infoLogHandler = new LoggingHandler(LogLevel.INFO);

            serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {

                    ChannelPipeline pipeline = ch.pipeline();

                    pipeline.addLast("debugLog", debugLogHandler);
                    pipeline.addLast("metricHandler", metricsHandler);
                    pipeline.addLast("idleHandler", new ServerIdleCheckHandler());

                    pipeline.addLast("infoLog", infoLogHandler);
                    pipeline.addLast("flushEnhance", new FlushConsolidationHandler(10, true));

//                    pipeline.addLast("auth", authHandler);
//                    pipeline.addLast(businessGroup, );
                }
            });

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
