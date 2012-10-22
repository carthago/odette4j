package com.de.grossmann.carthago.protocol.odette.codec;

import com.de.grossmann.carthago.protocol.odette.codec.data.OdetteExchangeBuffer;
import com.de.grossmann.carthago.protocol.odette.codec.data.StreamTransmissionBuffer;
import com.de.grossmann.carthago.protocol.odette.codec.data.StreamTransmissionHeader;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import static com.de.grossmann.carthago.common.ByteUtils.byteToHalfBytes;

public class OEBDecoder extends MessageToMessageDecoder<ByteBuf, OdetteExchangeBuffer> {
    
    private boolean useStreamTransmissionBuffer = true;
    
    public OEBDecoder(final boolean useStreamTransmissionBuffer) {
        super();
        
        this.useStreamTransmissionBuffer = useStreamTransmissionBuffer;
    }
    
    @Override
    public OdetteExchangeBuffer decode(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        
        OdetteExchangeBuffer oeb;
        
        if (this.useStreamTransmissionBuffer) {
            oeb = readSTB(msg).getOEB();
        } else {
            oeb = readOEB(msg);
        }
        
        return oeb;
    }

    public boolean isUseStreamTransmissionBuffer() {
        return useStreamTransmissionBuffer;
    }
    
    private StreamTransmissionBuffer readSTB(final ByteBuf in) {
        StreamTransmissionHeader sth = readSTH(in);
        OdetteExchangeBuffer oeb = readOEB(in);
        
        StreamTransmissionBuffer stb = new StreamTransmissionBuffer(sth, oeb);
        if (stb.isValid()) {
            return stb;    
        } else {
            // TODO throw DecodingException
            System.out.println("OEBDecoder.readSTB() - foo");
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
            System.out.println("STBDecoder.readHeader() - foo");
        }

        return null;
    }

    private OdetteExchangeBuffer readOEB(final ByteBuf in) {
        
        int payloadLength = in.readableBytes();
        
        byte[] payload = new byte[payloadLength];

        in.readBytes(payload, 0, payloadLength);

        if (payload.length == payloadLength) {
            return new OdetteExchangeBuffer(payload);
        } else {
            // TODO throw DecodingException
            System.out.println("STBDecoder.readData() - foo");
        }

        return null;
    }
}
