package me.kuuds.codec.modbus;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.UnsupportedMessageTypeException;

public interface ProtocolDataUnit {

    FunctionCode getFunctionCode();

    default void toBytes(ByteBuf byteBuf) {
        throw new UnsupportedOperationException("unimplemented toBytes method for " + getFunctionCode());
    }



}
