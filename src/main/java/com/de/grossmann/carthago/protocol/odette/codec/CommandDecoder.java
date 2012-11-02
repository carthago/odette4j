package com.de.grossmann.carthago.protocol.odette.codec;

import com.de.grossmann.carthago.protocol.odette.data.StreamTransmissionBuffer;
import com.de.grossmann.carthago.protocol.odette.data.StreamTransmissionHeader;
import com.de.grossmann.carthago.protocol.odette.data.command.Command;
import com.de.grossmann.carthago.protocol.odette.data.command.CommandIdentifier;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.de.grossmann.carthago.common.ByteUtils.byteToHalfBytes;

public class CommandDecoder extends MessageToMessageDecoder<ByteBuf, Command> {

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(CommandDecoder.class);
    }

    private boolean useStreamTransmissionBuffer = true;

    public CommandDecoder(final boolean useStreamTransmissionBuffer) {
        super();

        this.useStreamTransmissionBuffer = useStreamTransmissionBuffer;
    }

    @Override
    public Command decode(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        Command command;

        if (this.useStreamTransmissionBuffer) {
            StreamTransmissionBuffer streamTransmissionBuffer = readStreamTransmissionBuffer(msg);
            command = CommandIdentifier.identifyCommand(streamTransmissionBuffer.getOdetteExchangeBuffer());
        } else {
            command = readCommand(msg);
        }

        return command;
    }

    public boolean isUseStreamTransmissionBuffer() {
        return useStreamTransmissionBuffer;
    }

    private StreamTransmissionBuffer readStreamTransmissionBuffer(final ByteBuf in) throws CommandDecoderException {
        StreamTransmissionBuffer streamTransmissionBuffer;

        StreamTransmissionHeader streamTransmissionHeader = readStreamTransmissionHeader(in);
        byte[] odetteExchangeBuffer = readOdetteExchangeBuffer(in);

        streamTransmissionBuffer = new StreamTransmissionBuffer(streamTransmissionHeader, odetteExchangeBuffer);

        if (!streamTransmissionBuffer.isValid()) {
            throw new CommandDecoderException();
        }

        return streamTransmissionBuffer;
    }

    private StreamTransmissionHeader readStreamTransmissionHeader(final ByteBuf in) {

        byte[] sthVersionAndFlags = byteToHalfBytes(in.readByte());
        int sthLength = in.readMedium();

        StreamTransmissionHeader sth = new StreamTransmissionHeader(sthVersionAndFlags[0],
                sthVersionAndFlags[1],
                sthLength);

        if (sth.isValid()) {
            return sth;
        } else {
            // TODO throw DecodingException
            System.out.println("CommandDecoder.readStreamTransmissionHeader() - foo");
        }

        return null;
    }

    private byte[] readOdetteExchangeBuffer(final ByteBuf in) {

        int payloadLength = in.readableBytes();

        byte[] payload = new byte[payloadLength];

        in.readBytes(payload, 0, payloadLength);

        return payload;
    }

    private Command readCommand(final ByteBuf in) {

        int payloadLength = in.readableBytes();

        byte[] payload = new byte[payloadLength];

        in.readBytes(payload, 0, payloadLength);

        if (payload.length == payloadLength) {
            return CommandIdentifier.identifyCommand(payload);
        } else {
            // TODO throw DecodingException
            System.out.println("CommandDecoder.readCommand() - foo");
        }

        return null;
    }
}
