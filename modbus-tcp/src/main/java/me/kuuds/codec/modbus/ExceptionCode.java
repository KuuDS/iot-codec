package me.kuuds.codec.modbus;


public enum ExceptionCode {

    ILLEGAL_FUNCTION(0x01),
    ILLEGAL_DATA_ADDRESS(0x02),
    ILLEGAL_DATA_VALUE(0x03),
    SERVER_DEVICE_FAILURE(0x04),
    ACKNOWLEDGE(0x05),
    SERVER_DEVICE_BUSY(0x06),
    MEMORY_PARITY_ERROR(0x08),
    GATEWAY_PATH_UNAVAILABLE(0x0A),
    GATEWAY_TARGET_DEVICE_FAILED_TO_RESPOND(0x0B);

    private final int exceptionCode;

    ExceptionCode(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public int getExceptionCode() {
        return exceptionCode;
    }

    public static ExceptionCode fromCode(int code) {
        switch (code) {
            case 0x01:
                return ILLEGAL_FUNCTION;
            case 0x02:
                return ILLEGAL_DATA_ADDRESS;
            case 0x03:
                return ILLEGAL_DATA_VALUE;
            case 0x04:
                return SERVER_DEVICE_FAILURE;
            case 0x05:
                return ACKNOWLEDGE;
            case 0x06:
                return SERVER_DEVICE_BUSY;
            case 0x08:
                return MEMORY_PARITY_ERROR;
            case 0x0A:
                return GATEWAY_PATH_UNAVAILABLE;
            case 0x0B:
                return GATEWAY_TARGET_DEVICE_FAILED_TO_RESPOND;
            default:
                throw new IllegalArgumentException("Illegal exception code : " + code + ".");
        }
    }


}
