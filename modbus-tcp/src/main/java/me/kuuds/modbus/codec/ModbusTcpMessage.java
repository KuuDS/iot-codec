package me.kuuds.modbus.codec;

import io.netty.buffer.ByteBuf;

public class ModbusTcpMessage {


    /**
     * Header  7 Bytes
     * <p>
     * |Transaction Identifier|Protocol Identifier|Length|Unit Identifier|
     * |  2 Bytes |  2 Bytes |  2 Bytes | 1 Byte |
     * <p>
     * Transaction Identifier :  客户端定义
     * Protocol Identifier  0 表示 Modbus 协议 客户端初始化
     * Length: 包含Unit Identifier及Data的长度
     * Unit Identifier: modbus交互用 client定义
     */

    private ModbusTcpHeader header;
    private byte[] data;

    public ModbusTcpMessage(ModbusTcpHeader header, byte[] data) {
        this.data = data;
    }


}
