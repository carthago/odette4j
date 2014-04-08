package com.de.grossmann.carthago.protocol.odette.codec;

import com.de.grossmann.carthago.protocol.odette.data.StreamTransmissionBuffer;
import com.de.grossmann.carthago.protocol.odette.data.StreamTransmissionHeader;
import com.de.grossmann.carthago.protocol.odette.data.command.Command;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandEncoder extends MessageToByteEncoder<Command>
{

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(CommandEncoder.class);
    }

    private boolean useStreamTransmissionBuffer = true;

    public CommandEncoder(final boolean useStreamTransmissionBuffer) {
        super();

        this.useStreamTransmissionBuffer = useStreamTransmissionBuffer;
    }

    /**
     * Encode a message into a {@link io.netty.buffer.ByteBuf}. This method will be called for each written message that can be handled
     * by this encoder.
     *
     * @param ctx the {@link io.netty.channel.ChannelHandlerContext} which this {@link io.netty.handler.codec.MessageToByteEncoder} belongs to
     * @param msg the message to encode
     * @param out the {@link io.netty.buffer.ByteBuf} into which the encoded message will be written
     * @throws Exception is thrown if an error accour
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Command msg, ByteBuf out) throws Exception
    {
        if (this.useStreamTransmissionBuffer) {
            StreamTransmissionBuffer streamTransmissionBuffer = createStreamTransmissionBuffer(msg);

            out.writeBytes(streamTransmissionBuffer.getStreamTransmissionHeader().getBytes());
            out.writeBytes(streamTransmissionBuffer.getOdetteExchangeBuffer());
        } else {
            out.writeBytes(msg.getBytes());
        }

        LOGGER.info(">>> " + msg);
    }

    private StreamTransmissionBuffer createStreamTransmissionBuffer(final Command command) {
        
        final StreamTransmissionBuffer streamTransmissionBuffer;
        final StreamTransmissionHeader streamTransmissionHeader;
        final byte[] odetteExchangeBuffer = command.getBytes();
        
        streamTransmissionHeader = new StreamTransmissionHeader(odetteExchangeBuffer.length);
        streamTransmissionBuffer = new StreamTransmissionBuffer(streamTransmissionHeader, odetteExchangeBuffer);
        
        return streamTransmissionBuffer;
    }

}
