package com.de.grossmann.carthago.protocol.odette.codec;

import com.de.grossmann.carthago.protocol.odette.data.StreamTransmissionBuffer;
import com.de.grossmann.carthago.protocol.odette.data.StreamTransmissionHeader;
import com.de.grossmann.carthago.protocol.odette.data.command.Command;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandEncoder extends MessageToMessageEncoder<Command,ByteBuf> {

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(CommandEncoder.class);
    }

    private boolean useStreamTransmissionBuffer = true;

    public CommandEncoder(final boolean useStreamTransmissionBuffer) {
        super();

        this.useStreamTransmissionBuffer = useStreamTransmissionBuffer;
    }

    @Override
    public ByteBuf encode(ChannelHandlerContext ctx, Command command) throws Exception {

        ByteBuf byteBuf = Unpooled.EMPTY_BUFFER;

        if (this.useStreamTransmissionBuffer) {
            StreamTransmissionBuffer streamTransmissionBuffer = createStreamTransmissionBuffer(command);
            
            byteBuf.capacity(streamTransmissionBuffer.getStreamTransmissionHeader().getLength());
            byteBuf.writeBytes(streamTransmissionBuffer.getStreamTransmissionHeader().getBytes());
            byteBuf.writeBytes(streamTransmissionBuffer.getOdetteExchangeBuffer());
        } else {
            byteBuf.capacity(command.getBytes().length);
            byteBuf.writeBytes(command.getBytes());
        }

        return byteBuf;
    }

    public boolean isUseStreamTransmissionBuffer() {
        return useStreamTransmissionBuffer;
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
