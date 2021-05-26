package me.kuuds.codec.modbus;

public interface ApplicationDataUnit {

    ApplicationProtocolHeader getApplicationProtocolHeader();

    ProtocolDataUnit getProtocolDataUnit();

}
