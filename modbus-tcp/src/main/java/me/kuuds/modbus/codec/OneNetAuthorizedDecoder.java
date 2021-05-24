package me.kuuds.modbus.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.PreferHeapByteBufAllocator;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 *
 *
 * +-------+-----+------+-------+-----+
 * | type  | name|dev_sn|svrpwd |  id |
 * +-------+-----+------+-------+-----+
 * | 12    |  9  |  12  |   9   |  11 |
 * +-------+-----+------+-------+-----+
 */
public class OneNetAuthorizedDecoder extends DelimiterBasedFrameDecoder {


    private static final int TYPE_MAX_LENGTH = 11;
    private static final int NAME_MAX_LENGTH = 9;
    private static final int PHONE_MAX_LENGTH = 12;
    private static final int SVR_PWD_MAX_LENGTH = 9;
    private static final int ID_MAX_LENGTH = 11;
    private static final int DELIMITER = '\0';
    private static final ByteBuf DELIMITER_BYTE_BUF =PreferHeapByteBufAllocator.DEFAULT.buffer(1).writeByte(DELIMITER);
    private static final int MAX_FRAME_LENGTH = TYPE_MAX_LENGTH + NAME_MAX_LENGTH + PHONE_MAX_LENGTH + SVR_PWD_MAX_LENGTH + ID_MAX_LENGTH;

    public OneNetAuthorizedDecoder() {
        super(MAX_FRAME_LENGTH, DELIMITER_BYTE_BUF);
    }



}
