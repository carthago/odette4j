package com.de.grossmann.carthago.protocol.odette.codec;

import com.de.grossmann.carthago.protocol.odette.codec.data.StreamTransmissionBuffer;
import com.de.grossmann.carthago.protocol.odette.codec.data.StreamTransmissionHeader;
import com.de.grossmann.carthago.protocol.odette.codec.data.command.Command;
import com.de.grossmann.carthago.protocol.odette.codec.data.command.CommandIdentifier;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import static com.de.grossmann.carthago.common.ByteUtils.byteToHalfBytes;

public class CommandDecoder extends MessageToMessageDecoder<ByteBuf, Command> {
    
    private boolean useStreamTransmissionBuffer = true;
    
    public CommandDecoder(final boolean useStreamTransmissionBuffer) {
        super();
        
        this.useStreamTransmissionBuffer = useStreamTransmissionBuffer;
    }
    
    @Override
    public Command decode(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        
        Command command;
        
        if (this.useStreamTransmissionBuffer) {
            command = CommandIdentifier.identifyCommand(readSTB(msg).getOEB());
        } else {
            command = readCommand(msg);
        }
        
        return command;
    }

    public boolean isUseStreamTransmissionBuffer() {
        return useStreamTransmissionBuffer;
    }
    
    private StreamTransmissionBuffer readSTB(final ByteBuf in) {
        
        StreamTransmissionHeader sth = readSTH(in);
        byte[] oeb = readOEB(in);
        
        StreamTransmissionBuffer stb = new StreamTransmissionBuffer(sth, oeb);
        if (stb.isValid()) {
            return stb;    
        } else {
            // TODO throw DecodingException
            System.out.println("CommandDecoder.readSTB() - foo");
        }
        return null;
    }
    
    private StreamTransmissionHeader readSTH(final ByteBuf in) {
       
        byte[] sthVersionAndFlags = byteToHalfBytes(in.readByte());
        int sthLength = in.readMedium();
        
        StreamTransmissionHeader sth = new StreamTransmissionHeader(sthVersionAndFlags[0],
                sthVersionAndFlags[1],
                sthLength);
        
        if (sth.isValid()) {
            return sth;
        } else {
            // TODO throw DecodingException
            System.out.println("CommandDecoder.readSTH() - foo");
        }

        return null;
    }

    private byte[] readOEB(final ByteBuf in) {
        
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
