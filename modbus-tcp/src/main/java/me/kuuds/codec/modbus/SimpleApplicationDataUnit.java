package me.kuuds.codec.modbus;

import lombok.Data;

@Data
public class SimpleApplicationDataUnit implements ApplicationDataUnit{

    private ApplicationProtocolHeader applicationProtocolHeader;
    private ProtocolDataUnit protocolDataUnit;

    public SimpleApplicationDataUnit(ApplicationProtocolHeader applicationProtocolHeader, ProtocolDataUnit protocolDataUnit) {
        this.applicationProtocolHeader = applicationProtocolHeader;
        this.protocolDataUnit = protocolDataUnit;
    }

}
