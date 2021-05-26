package me.kuuds.codec.modbus;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ModbusTcpEncoder extends MessageToByteEncoder<ApplicationDataUnit> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ApplicationDataUnit applicationDataUnit, ByteBuf byteBuf) throws Exception {
        applicationDataUnit.getProtocolDataUnit().toBytes(byteBuf);
    }
}
