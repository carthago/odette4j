package com.de.grossmann.carthago.protocol.odette;

import javax.net.ssl.SSLEngine;

import com.de.grossmann.carthago.protocol.odette.codec.OEBDecoder;
import com.de.grossmann.carthago.protocol.odette.codec.Transport;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.ssl.SslHandler;


public class OFTPClientInitializer extends ChannelInitializer<Channel>
{
    private Transport transport = Transport.TCPIP;
    
    // TODO maybe we could get the constants from the StreamTransmissionHeader class
    // TODO what is is max frame length in our case (MAX_STB_SIZE?)
    private static int MAX_FRAME_LENGTH = 8192;
    
    // The length field starts at second byte.
    private static int LENGTHFIELD_OFFESET = 1;
    
    // The length field is three bytes long.
    private static int LENGTHFIELD_LENGTH = 3;

    // The value of the length field contains the header length, too.
    private static int LENGTH_ADJUSTMENT = -4;
    
    // We do not need to strip the header.
    private static int INITIAL_BYTES_TO_STRIP = 0; 
        
    public OFTPClientInitializer(final Transport transport) {
        this.transport = transport;
    }
    
    @Override
    public void initChannel(Channel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();

        // Attention... see fall through
        switch (this.transport)
        {
            case TLS:
                SSLEngine engine =
                    OFTPSslContextFactory.getClientContext().createSSLEngine();
                engine.setUseClientMode(true);
                engine.setEnabledProtocols(new String[] {"TLSv1"});

                pipeline.addLast("tls-handler", new SslHandler(engine));
            case TCPIP:
                LengthFieldBasedFrameDecoder stbFrameDecoder = new LengthFieldBasedFrameDecoder(
                            MAX_FRAME_LENGTH,
                            LENGTHFIELD_OFFESET,
                            LENGTHFIELD_LENGTH,
                            LENGTH_ADJUSTMENT,
                            INITIAL_BYTES_TO_STRIP);

                pipeline.addLast("stb-framer", stbFrameDecoder);

            default:
                pipeline.addLast("oeb-decoder", new OEBDecoder(true));
                //pipeline.addLast("decoder", new StringDecoder());
                //pipeline.addLast("encoder", new StringEncoder());

                // and then business logic.
                pipeline.addLast("handler", new OFTPClientHandler());
                break;
        }
        
        
    }
    
    

}
