package me.kuuds.modbus.codec;

public enum ModbusFunction {

    READ_COILS(0x01, false),
    READ_DISCRETE_INPUTS(0x02, false),
    READ_HOLDING_REGISTERS(0x03, false),
    READ_INPUT_REGISTERS(0x04, false),
    WRITE_SINGLE_COIL(0x05, false),
    WRITE_SINGLE_REGISTER(0x06, false),
    READ_EXCEPTION_STATUS(0x07, true),
    DIAGNOSTICS(0x08, true),
    GET_COMM_EVENT_COUNTER(0x0B, true),
    GET_COMM_EVENT_LOG(0x0C, true),
    WRITE_MULTIPLE_COILS(0x0F, false),
    WRITE_MULTIPLE_REGISTERS(0x10, false),
    REPORT_SERVER_ID(0x11, true),
    READ_FILE_RECORD(0x14, false),
    WRITE_FILE_RECORD(0x15, false),
    MASK_WRITE_REGISTERS(0x16, false),
    RW_MULTIPLE_REGISTERS(0x17, false),
    READ_FIFO_QUEUE(0x18, false),
    ENCAPSULATED_INTERFACE_TRANSPORT(0x2B, false),
    CAN_OPEN_GENERAL_REFERENCE(0x0D, false),
    READ_DEVICE_IDENTIFICATION(0x0E, false);

    private final int code;
    private final boolean serialOnly;

    ModbusFunction(int code, boolean serialOnly) {
        this.code = code;
        this.serialOnly = serialOnly;
    }

    public int getCode() {
        return code;
    }

    public boolean isSerialOnly() {
        return serialOnly;
    }

    public static ModbusFunction fromCode(int code) {
        switch (code) {
            case 0x01:
                return READ_COILS;
            case 0x02:
                return READ_DISCRETE_INPUTS;
            case 0x03:
                return READ_HOLDING_REGISTERS;
            case 0x04:
                return READ_INPUT_REGISTERS;
            case 0x05:
                return WRITE_SINGLE_COIL;
            case 0x06:
                return WRITE_SINGLE_REGISTER;
            case 0x07:
                return READ_EXCEPTION_STATUS;
            case 0x08:
                return DIAGNOSTICS;
            case 0x0B:
                return GET_COMM_EVENT_COUNTER;
            case 0x0C:
                return GET_COMM_EVENT_LOG;
            case 0x0F:
                return WRITE_MULTIPLE_COILS;
            case 0x10:
                return WRITE_MULTIPLE_REGISTERS;
            case 0x11:
                return REPORT_SERVER_ID;
            case 0x14:
                return READ_FILE_RECORD;
            case 0x15:
                return WRITE_FILE_RECORD;
            case 0x16:
                return MASK_WRITE_REGISTERS;
            case 0x17:
                return RW_MULTIPLE_REGISTERS;
            case 0x18:
                return READ_FIFO_QUEUE;
            case 0x2B:
                return ENCAPSULATED_INTERFACE_TRANSPORT;
            case 0x0D:
                return CAN_OPEN_GENERAL_REFERENCE;
            case 0x0E:
                return READ_DEVICE_IDENTIFICATION;
            default:
                throw new IllegalArgumentException("illegal function code" + code + ".");
        }
    }

}
