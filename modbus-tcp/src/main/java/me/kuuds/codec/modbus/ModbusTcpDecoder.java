package me.kuuds.codec.modbus;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class ModbusTcpDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (invalidHeaderLength(in)) {
            throw new DecoderException("invalid header length.");
        }
        int transactionIdentifier = in.readUnsignedShort();
        int protocolIdentifier = in.readUnsignedShort();
        int length = in.readUnsignedShort();
        if (in.readableBytes() < length && length >= 2) {
            throw new DecoderException("invalid pdu length");
        }
        int unitIdentifier = in.readUnsignedByte();
        FunctionCode functionCode = FunctionCode.fromCode(in.readUnsignedByte());
        byte[] data = new byte[length - 2];
        in.readBytes(data);
        ApplicationProtocolHeader header = ApplicationProtocolHeader.builder()
                .transactionIdentifier(transactionIdentifier)
                .protocolIdentifier(protocolIdentifier)
                .unitIdentifier(unitIdentifier)
                .length(length)
                .build();
        ProtocolDataUnit protocolDataUnit = Response.response(functionCode, in.retainedSlice());
        ApplicationDataUnit applicationDataUnit = new SimpleApplicationDataUnit(header, protocolDataUnit);
        out.add(applicationDataUnit);
    }

    private boolean invalidHeaderLength(ByteBuf in) {
        return in.readableBytes() < ProtocolConstants.HEADER_BYTES_LENGTH;
    }


}
