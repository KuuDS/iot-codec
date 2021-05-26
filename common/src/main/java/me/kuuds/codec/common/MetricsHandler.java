package me.kuuds.codec.common;

import io.micrometer.core.instrument.MeterRegistry;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.atomic.AtomicLong;


@ChannelHandler.Sharable
public class MetricsHandler extends ChannelDuplexHandler {

    private final AtomicLong totalConnectionCounter = new AtomicLong();

    public MetricsHandler(MeterRegistry meterRegistry) {
        meterRegistry.gauge("totalConnectionNumber", totalConnectionCounter, AtomicLong::doubleValue);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        totalConnectionCounter.incrementAndGet();
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        totalConnectionCounter.decrementAndGet();
        super.channelInactive(ctx);
    }
}
