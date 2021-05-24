package me.kuuds.modbus.codec;

import lombok.Builder;

/**
 *
 * Modbus TCP Message Header
 *
 * @author kuuds
 * @since 1.0
 *
 * Header  7 Bytes
 * <p>
 * |Transaction Identifier|Protocol Identifier|Length|Unit Identifier|
 * |  2 Bytes |  2 Bytes |  2 Bytes | 1 Byte |
 * <p>
 *
 *     <ol>
 * Transaction Identifier :  客户端定义
 * Protocol Identifier  0 表示 Modbus 协议 客户端初始化
 * Length: 包含Unit Identifier及Data的长度
 * Unit Identifier: modbus交互用 client定义
 *
 *     </ol>
 *
 */
public class ModbusTcpHeader {

    private final int transactionIdentifier;
    private final int protocolIdentifier;
    private final int length;
    private final int unitIdentifier;
    private final ModbusFunction function;

    @Builder
    private ModbusTcpHeader(
            int transactionIdentifier,
            int protocolIdentifier,
            int unitIdentifier,
            int length,
            ModbusFunction function) {
        this.transactionIdentifier = transactionIdentifier;
        this.protocolIdentifier = protocolIdentifier;
        this.unitIdentifier = unitIdentifier;
        this.function = function;
        this.length = length;
    }


}
