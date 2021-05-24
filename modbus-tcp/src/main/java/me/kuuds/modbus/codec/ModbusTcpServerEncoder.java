package me.kuuds.modbus.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ModbusTcpServerEncoder extends MessageToByteEncoder<ModbusTcpMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ModbusTcpMessage modbusTcpMessage, ByteBuf byteBuf) throws Exception {

    }
}
