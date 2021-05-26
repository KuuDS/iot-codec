package me.kuuds.codec.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * OneNet Tcp Authorized Message Frame Decoder
 * <p>
 * Ex: *${ProductKey}#${DeviceSn}#${PLACEHOLDER}*
 * <p>
 * ProductKey:
 * DeviceSn:
 * PLACEHOLDER: 0
 */

@Slf4j
public class OneNetAuthorizedMessageDecoder extends ByteToMessageDecoder {

    private static final int PRODUCT_KEY_MAX_LENGTH = 32;
    private static final int PRODUCT_KEY_MIN_LENGTH = 6;
    private static final int DEV_SN_MAX_LENGTH = 32;
    private static final int DEV_SN_MIN_LENGTH = 6;
    private static final int PLACEHOLDER_MAX_LENGTH = 1;
    private static final int PLACEHOLDER_MIN_LENGTH = 0;
    private static final byte BOUND_DELIMITER = '*';
    private static final byte FILED_DELIMITER = '#';
    private static final int MAX_FRAME_LENGTH = BOUND_DELIMITER + PRODUCT_KEY_MAX_LENGTH + FILED_DELIMITER + DEV_SN_MAX_LENGTH + FILED_DELIMITER + PLACEHOLDER_MAX_LENGTH + BOUND_DELIMITER;
    private static final int MIN_FRAME_LENGTH = BOUND_DELIMITER + PRODUCT_KEY_MIN_LENGTH + FILED_DELIMITER + DEV_SN_MIN_LENGTH + FILED_DELIMITER + PLACEHOLDER_MIN_LENGTH + BOUND_DELIMITER;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        if (in.readableBytes() < MIN_FRAME_LENGTH) {
            throw new DecoderException("invalid auth package. length:" + in.readableBytes());
        }

        if (in.readByte() != BOUND_DELIMITER) {
            throw new DecoderException("invalid content for protocol.");
        }

        String productKey = readKey(in.retainedSlice(), FILED_DELIMITER, PRODUCT_KEY_MAX_LENGTH);
        String deviceSn =  readKey(in.retainedSlice(), FILED_DELIMITER, DEV_SN_MAX_LENGTH);
        readKey(in.retainedSlice(), BOUND_DELIMITER, PLACEHOLDER_MAX_LENGTH);
        OneNetAuthorizedMessage authorizedMessage = new OneNetAuthorizedMessage();
        authorizedMessage.setProductKey(productKey);
        authorizedMessage.setDeviceSn(deviceSn);
        out.add(authorizedMessage);
    }

    private String readKey(ByteBuf in, byte delimiter, int maxLength) {
        int fieldLength = 0;
        while (in.readableBytes() > 0) {
            if (in.readByte() == delimiter) {
                break;
            }
            if (fieldLength >= maxLength) {
                throw new DecoderException("too long for field.");
            }
            fieldLength++;
        }

        return in.slice(0, fieldLength).toString();
    }
}
