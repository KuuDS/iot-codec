package me.kuuds.codec.example.modbus;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import me.kuuds.codec.common.MetricsHandler;
import me.kuuds.codec.common.OneNetAuthorizedMessageDecoder;
import me.kuuds.codec.common.ServerIdleCheckHandler;

public class ModbusChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final LoggingHandler debugLogHandler = new LoggingHandler(LogLevel.DEBUG);
    private final LoggingHandler infoLogHandler = new LoggingHandler(LogLevel.INFO);
    private final MetricsHandler metricsHandler;

    public  ModbusChannelInitializer (MetricsHandler metricsHandler) {
        this.metricsHandler = metricsHandler;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("debugLog", debugLogHandler);
        pipeline.addLast("metricHandler", metricsHandler);
        pipeline.addLast("idleHandler", new ServerIdleCheckHandler());

        pipeline.addLast("oneNetAuth", new OneNetAuthorizedMessageDecoder());
        pipeline.addLast("auth", new TbAuthHandler());
        pipeline.addLast("infoLog", infoLogHandler);
    }
}
