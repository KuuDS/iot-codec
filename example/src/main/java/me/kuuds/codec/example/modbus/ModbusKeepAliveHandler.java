package me.kuuds.codec.example.modbus;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

public class ModbusKeepAliveHandler extends ChannelDuplexHandler {

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
    }
}
