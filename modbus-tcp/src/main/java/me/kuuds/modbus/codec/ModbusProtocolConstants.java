package me.kuuds.modbus.codec;

public interface ModbusProtocolConstants {

     int HEADER_BYTES_LENGTH = 7;
     int PDU_MAX_LENGTH = 253;
     int TCP_PACKET_MAX_LENGTH = HEADER_BYTES_LENGTH + PDU_MAX_LENGTH;


}
