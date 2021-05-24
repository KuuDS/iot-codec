package me.kuuds.codec.common;

import io.micrometer.core.instrument.MeterRegistry;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.atomic.AtomicLong;


@ChannelHandler.Sharable
public class MetricsHandler extends ChannelDuplexHandler {

    private AtomicLong totalConnectionNumber = new AtomicLong();

    public MetricsHandler(MeterRegistry meterRegistry) {
        meterRegistry.gauge("totalConnectionNumber", totalConnectionNumber, AtomicLong::doubleValue);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        totalConnectionNumber.incrementAndGet();
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        totalConnectionNumber.decrementAndGet();
        super.channelInactive(ctx);
    }
}
