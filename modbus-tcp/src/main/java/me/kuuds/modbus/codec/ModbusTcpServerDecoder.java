package me.kuuds.modbus.codec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ModbusTcpServerDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (invalidHeaderLength(in)) {
            // TODO
            log.info("");
            return;
        }
        int transactionIdentifier = in.readUnsignedShort();
        int protocolIdentifier = in.readUnsignedShort();
        int length = in.readUnsignedShort();
        if (in.readableBytes() < length) {
            // TODO
            log.info("");
            return;
        }
        int unitIdentifier = in.readUnsignedByte();
        ModbusFunction function = ModbusFunction.fromCode(in.readUnsignedByte());
        byte[] data = new byte[length - 2];
        in.readBytes(data);
        ModbusTcpHeader header = ModbusTcpHeader.builder().transactionIdentifier(transactionIdentifier)
                .protocolIdentifier(protocolIdentifier)
                .unitIdentifier(unitIdentifier)
                .function(function)
                .length(length)
                .build();
        ModbusTcpMessage modbusTcpMessage = new ModbusTcpMessage(header, data);
        out.add(modbusTcpMessage);
    }

    private boolean invalidHeaderLength(ByteBuf in) {
        return in.readableBytes() < ModbusProtocolConstants.HEADER_BYTES_LENGTH;
    }


}
