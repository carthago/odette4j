package com.de.grossmann.carthago.protocol.odette.codec;

import com.de.grossmann.carthago.protocol.odette.data.StreamTransmissionBuffer;
import com.de.grossmann.carthago.protocol.odette.data.StreamTransmissionHeader;
import com.de.grossmann.carthago.protocol.odette.data.commands.Command;
import com.de.grossmann.carthago.protocol.odette.data.commands.CommandIdentifier;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.de.grossmann.carthago.common.ByteUtils.byteToHalfBytes;

public class CommandDecoder extends ByteToMessageDecoder
{

    private static final Logger LOGGER;

    static
    {
        LOGGER = LoggerFactory.getLogger(CommandDecoder.class);
    }

    private boolean useStreamTransmissionBuffer = true;

    public CommandDecoder(final boolean useStreamTransmissionBuffer)
    {
        super();

        this.useStreamTransmissionBuffer = useStreamTransmissionBuffer;
    }

    /**
     * Decode the from one {@link io.netty.buffer.ByteBuf} to an other. This method will be called till either the input
     * {@link io.netty.buffer.ByteBuf} has nothing to read anymore, till nothing was read from the input {@link io.netty.buffer.ByteBuf} or till
     * this method returns {@code null}.
     *
     * @param ctx the {@link io.netty.channel.ChannelHandlerContext} which this {@link io.netty.handler.codec.ByteToMessageDecoder} belongs to
     * @param in  the {@link io.netty.buffer.ByteBuf} from which to read data
     * @param out the {@link java.util.List} to which decoded messages should be added
     * @throws Exception is thrown if an error accour
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
        if (in.isReadable())
        {
            Command command;

            if (this.useStreamTransmissionBuffer)
            {
                StreamTransmissionBuffer streamTransmissionBuffer = readStreamTransmissionBuffer(in);
                command = CommandIdentifier.identifyCommand(streamTransmissionBuffer.getOdetteExchangeBuffer());
            }
            else
            {
                command = readCommand(in);
            }

            if (command != null)
            {
                LOGGER.debug("<<< " + command);
                out.add(command);
            }
        }
    }

    private StreamTransmissionBuffer readStreamTransmissionBuffer(final ByteBuf in) throws CommandDecoderException
    {

        if (!in.isReadable())
        {
            throw new CommandDecoderException("Unable to read StreamTransmissionBuffer.");
        }

        StreamTransmissionBuffer streamTransmissionBuffer;

        StreamTransmissionHeader streamTransmissionHeader = readStreamTransmissionHeader(in);
        byte[] odetteExchangeBuffer = readOdetteExchangeBuffer(in);

        streamTransmissionBuffer = new StreamTransmissionBuffer(streamTransmissionHeader, odetteExchangeBuffer);

        if (!streamTransmissionBuffer.isValid())
        {
            throw new CommandDecoderException("Invalid StreamTransmissionBuffer read.");
        }

        return streamTransmissionBuffer;
    }

    private StreamTransmissionHeader readStreamTransmissionHeader(final ByteBuf in) throws CommandDecoderException
    {

        if (!in.isReadable())
        {
            throw new CommandDecoderException("Unable to read StreamTransmissionHeader.");
        }

        byte[] sthVersionAndFlags = byteToHalfBytes(in.readByte());
        int sthLength = in.readMedium();

        StreamTransmissionHeader streamTransmissionHeader = new StreamTransmissionHeader(sthVersionAndFlags[0],
                                                                                         sthVersionAndFlags[1],
                                                                                         sthLength);

        if (!streamTransmissionHeader.isValid())
        {
            throw new CommandDecoderException("Invalid StreamTransmissionHeader read.");
        }

        return streamTransmissionHeader;
    }

    private byte[] readOdetteExchangeBuffer(final ByteBuf in)
    {

        int payloadLength = in.readableBytes();

        byte[] payload = new byte[payloadLength];

        in.readBytes(payload, 0, payloadLength);

        return payload;
    }

    private Command readCommand(final ByteBuf in)
    {

        int payloadLength = in.readableBytes();

        byte[] payload = new byte[payloadLength];

        in.readBytes(payload, 0, payloadLength);

        if (payload.length == payloadLength)
        {
            return CommandIdentifier.identifyCommand(payload);
        }
        else
        {
            // TODO throw DecodingException
            System.out.println("CommandDecoder.readCommand() - foo");
        }

        return null;
    }
}
