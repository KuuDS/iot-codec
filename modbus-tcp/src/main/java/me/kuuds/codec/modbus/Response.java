package me.kuuds.codec.modbus;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import lombok.Getter;

@SuppressWarnings("DuplicatedCode")
public interface Response extends ProtocolDataUnit {

    default boolean isSuccess() {
        return !getFunctionCode().isError();
    }

    static Response response(FunctionCode code, ByteBuf buf) {
        switch (code) {
            case READ_COILS:
            case EX_READ_COILS:
                return new ReadCoilsResponse(code, buf);
            case READ_DISCRETE_INPUTS:
            case EX_READ_DISCRETE_INPUTS:
                return new ReadDiscreteInputResponse(code, buf);
            case READ_HOLDING_REGISTERS:
            case EX_READ_HOLDING_REGISTERS:
                return new ReadHoldingRegistersResponse(code, buf);
            case READ_INPUT_REGISTERS:
            case EX_READ_INPUT_REGISTERS:
                return new ReadInputRegistersResponse(code, buf);
            default:
                throw new UnsupportedMessageTypeException("unsupported function code:" + code);
        }
    }

    @Getter
    class ReadCoilsResponse implements Response {

        private final FunctionCode functionCode;
        private int byteCount;
        private byte[] coilStatus;
        private ExceptionCode exceptionCode;

        ReadCoilsResponse(FunctionCode code, ByteBuf buf) {
            this.functionCode = code;
            if (functionCode == FunctionCode.READ_COILS) {
                if (buf.readableBytes() < 1) {
                    throw new DecoderException("invalid length for byte count: " + buf.readableBytes());
                }
                byteCount = buf.readByte();
                if (buf.readableBytes() < byteCount) {
                    throw new DecoderException("invalid length for coil status:" + buf.readableBytes());
                }
                coilStatus = new byte[byteCount];
                buf.readBytes(coilStatus);
            } else if (functionCode == FunctionCode.EX_READ_COILS) {
                if (buf.readableBytes() < 1) {
                    throw new DecoderException("invalid length for exception code: " + buf.readableBytes());
                }
                exceptionCode = ExceptionCode.fromCode(buf.readByte());
            }
        }
    }

    @Getter
    class ReadDiscreteInputResponse implements Response {

        private final FunctionCode functionCode;
        private int byteCount;
        private byte[] inputStatus;
        private ExceptionCode exceptionCode;

        ReadDiscreteInputResponse(FunctionCode code, ByteBuf buf) {
            this.functionCode = code;
            if (functionCode == FunctionCode.READ_DISCRETE_INPUTS) {
                if (buf.readableBytes() < 1) {
                    throw new DecoderException("invalid length for byte count: " + buf.readableBytes());
                }
                byteCount = buf.readByte();
                if (buf.readableBytes() < byteCount) {
                    throw new DecoderException("invalid length for input status:" + buf.readableBytes());
                }
                inputStatus = new byte[byteCount];
                buf.readBytes(inputStatus);
            } else if (functionCode == FunctionCode.EX_READ_DISCRETE_INPUTS) {
                if (buf.readableBytes() < 1) {
                    throw new DecoderException("invalid length for exception code: " + buf.readableBytes());
                }
                exceptionCode = ExceptionCode.fromCode(buf.readByte());
            }
        }
    }

    @Getter
    class ReadHoldingRegistersResponse implements Response {

        private final FunctionCode functionCode;
        private int byteCount;
        private byte[] inputStatus;
        private ExceptionCode exceptionCode;

        ReadHoldingRegistersResponse(FunctionCode code, ByteBuf buf) {
            this.functionCode = code;
            if (functionCode == FunctionCode.READ_HOLDING_REGISTERS) {
                if (buf.readableBytes() < 1) {
                    throw new DecoderException("invalid length for byte count: " + buf.readableBytes());
                }
                byteCount = buf.readByte();
                if (buf.readableBytes() < byteCount) {
                    throw new DecoderException("invalid length for input status:" + buf.readableBytes());
                }
                inputStatus = new byte[byteCount];
                buf.readBytes(inputStatus);
            } else if (functionCode == FunctionCode.EX_READ_DISCRETE_INPUTS) {
                if (buf.readableBytes() < 1) {
                    throw new DecoderException("invalid length for exception code: " + buf.readableBytes());
                }
                exceptionCode = ExceptionCode.fromCode(buf.readByte());
            }
        }
    }

    @Getter
    class ReadInputRegistersResponse implements Response {

        private final FunctionCode functionCode;
        private int byteCount;
        private byte[] inputRegisters;
        private ExceptionCode exceptionCode;

        ReadInputRegistersResponse(FunctionCode code, ByteBuf buf) {
            this.functionCode = code;
            if (functionCode == FunctionCode.READ_HOLDING_REGISTERS) {
                if (buf.readableBytes() < 1) {
                    throw new DecoderException("invalid length for byte count: " + buf.readableBytes());
                }
                byteCount = buf.readByte();
                if (buf.readableBytes() < byteCount) {
                    throw new DecoderException("invalid length for input status:" + buf.readableBytes());
                }
                inputRegisters = new byte[byteCount];
                buf.readBytes(inputRegisters);
            } else if (functionCode == FunctionCode.EX_READ_DISCRETE_INPUTS) {
                if (buf.readableBytes() < 1) {
                    throw new DecoderException("invalid length for exception code: " + buf.readableBytes());
                }
                exceptionCode = ExceptionCode.fromCode(buf.readByte());
            }
        }
    }
}
