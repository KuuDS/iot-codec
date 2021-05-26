package me.kuuds.codec.modbus;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.UnsupportedMessageTypeException;

public interface Request extends ProtocolDataUnit {

    static Request request(FunctionCode code, int... field) {
        switch (code) {
            case READ_COILS:
                return new ReadCoilsRequest(field[0], field[1]);
            case READ_DISCRETE_INPUTS:
                return new ReadDiscreteInputRequest(field[0], field[1]);
            case READ_HOLDING_REGISTERS:
                return new ReadHoldingRegistersRequest(field[0], field[1]);
            case READ_INPUT_REGISTERS:
                return new ReadInputRegistersRequest(field[0], field[1]);
            default:
                throw new UnsupportedMessageTypeException("unsupported function code:" + code);
        }
    }

    class ReadCoilsRequest implements Request {

        private final int startingAddress;
        private final int quantity;

        ReadCoilsRequest(int startingAddress, int quantity) {
            this.startingAddress = startingAddress;
            this.quantity = quantity;
        }

        @Override
        public FunctionCode getFunctionCode() {
            return FunctionCode.READ_COILS;
        }

        @Override
        public void toBytes(ByteBuf byteBuf) {
            if (byteBuf.isWritable(3)) {
                throw new EncoderException("need at least 3 bytes for writing.");
            }
            byteBuf.writeShort(startingAddress);
            byteBuf.writeShort(quantity);
        }

    }

    class ReadDiscreteInputRequest implements Request {

        private final int startingAddress;
        private final int quantity;

        ReadDiscreteInputRequest(int startingAddress, int quantity) {
            this.startingAddress = startingAddress;
            this.quantity = quantity;
        }

        @Override
        public FunctionCode getFunctionCode() {
            return FunctionCode.READ_DISCRETE_INPUTS;
        }

        @Override
        public void toBytes(ByteBuf byteBuf) {
            if (byteBuf.isWritable(3)) {
                throw new EncoderException("need at least 3 bytes for writing.");
            }
            byteBuf.writeShort(startingAddress);
            byteBuf.writeShort(quantity);
        }

    }

    class ReadHoldingRegistersRequest implements Request {

        private final int startingAddress;
        private final int quantity;

        ReadHoldingRegistersRequest(int startingAddress, int quantity) {
            this.startingAddress = startingAddress;
            this.quantity = quantity;
        }

        @Override
        public FunctionCode getFunctionCode() {
            return FunctionCode.READ_HOLDING_REGISTERS;
        }

        @Override
        public void toBytes(ByteBuf byteBuf) {
            if (byteBuf.isWritable(3)) {
                throw new EncoderException("need at least 3 bytes for writing.");
            }
            byteBuf.writeShort(startingAddress);
            byteBuf.writeShort(quantity);
        }

    }

    class ReadInputRegistersRequest implements Request {

        private final int startingAddress;
        private final int quantity;

        ReadInputRegistersRequest(int startingAddress, int quantity) {
            this.startingAddress = startingAddress;
            this.quantity = quantity;
        }

        @Override
        public FunctionCode getFunctionCode() {
            return FunctionCode.READ_INPUT_REGISTERS;
        }

        @Override
        public void toBytes(ByteBuf byteBuf) {
            if (byteBuf.isWritable(3)) {
                throw new EncoderException("need at least 3 bytes for writing.");
            }
            byteBuf.writeShort(startingAddress);
            byteBuf.writeShort(quantity);
        }

    }
}
