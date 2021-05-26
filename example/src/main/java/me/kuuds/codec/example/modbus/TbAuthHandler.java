package me.kuuds.codec.example.modbus;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.kuuds.codec.common.OneNetAuthorizedMessage;
import me.kuuds.codec.modbus.ModbusTcpDecoder;
import me.kuuds.codec.modbus.ModbusTcpEncoder;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TbAuthHandler extends SimpleChannelInboundHandler<OneNetAuthorizedMessage> {

    private Executor executor = Executors.newWorkStealingPool();

    public TbAuthHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, OneNetAuthorizedMessage oneNetAuthorizedMessage) throws Exception {
        // do auth, and write in
        ListenableFuture<Object> authFuture = Futures.submit(Object::new, executor);

        Futures.addCallback(authFuture, new FutureCallback<Object>() {
            @Override
            public void onSuccess(@Nullable Object result) {
                authPass(ctx, result);
            }

            @Override
            public void onFailure(Throwable t) {
                authReject(ctx, t);
            }
        }, executor);
    }

    private void authPass(ChannelHandlerContext ctx, Object obj) {
        DeviceSessionCtx deviceSessionCtx = new DeviceSessionCtx();
        ModbusTcpTransportHandler modbusTcpTransportHandler = new ModbusTcpTransportHandler(deviceSessionCtx);
        ctx.pipeline().remove("oneNetAuth");
        ctx.pipeline().remove("oneNetAuth");
        ctx.pipeline().addLast("keepalive", new ModbusKeepAliveHandler());
        ctx.pipeline().addLast("modbusDecode", new ModbusTcpDecoder());
        ctx.pipeline().addLast("modbusEncode", new ModbusTcpEncoder());

        ctx.pipeline().addLast(modbusTcpTransportHandler);
    }

    private void authReject(ChannelHandlerContext ctx, Throwable t) {
        ctx.channel().close();
    }
}
