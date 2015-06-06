package com.de.grossmann.carthago.protocol.odette;


import com.de.grossmann.carthago.protocol.odette.codec.CommandDecoder;
import com.de.grossmann.carthago.protocol.odette.codec.CommandEncoder;
import com.de.grossmann.carthago.protocol.odette.codec.Transport;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.ssl.SslHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLEngine;


public class OFTPClientInitializer extends ChannelInitializer<Channel> {

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(OFTPClientInitializer.class);
    }

    private Transport transport = Transport.TCP;

    // TODO maybe we could get the constants from the StreamTransmissionHeader class
    // TODO what is is max frame length in our case (MAX_SIZE?)
    private static final int MAX_FRAME_LENGTH = 8192;

    // The length field starts at second byte.
    private static final int LENGTHFIELD_OFFESET = 1;

    // The length field is three bytes long.
    private static final int LENGTHFIELD_LENGTH = 3;

    // The value of the length field contains the header length, too.
    private static final int LENGTH_ADJUSTMENT = -4;

    // We do not need to strip the header.
    private static final int INITIAL_BYTES_TO_STRIP = 0;

    public OFTPClientInitializer(final Transport transport) {
        this.transport = transport;
    }

    @Override
    public void initChannel(Channel channel) throws Exception {
        ChannelPipeline channelPipeline = channel.pipeline();

        // Attention... see fall through
        switch (this.transport) {
            case TLS:
                SSLEngine engine =
                        OFTPSSLContextFactory.getClientContext().createSSLEngine();
                engine.setUseClientMode(true);
                engine.setEnabledProtocols(new String[]{"TLSv1"});

                channelPipeline.addLast("tls-handler", new SslHandler(engine));
            case TCP:
                LengthFieldBasedFrameDecoder stbFrameDecoder = new LengthFieldBasedFrameDecoder(
                        MAX_FRAME_LENGTH,
                        LENGTHFIELD_OFFESET,
                        LENGTHFIELD_LENGTH,
                        LENGTH_ADJUSTMENT,
                        INITIAL_BYTES_TO_STRIP);

                channelPipeline.addLast("stb-framer", stbFrameDecoder);
            default:
                channelPipeline.addLast("command-decoder", new CommandDecoder(true));
                channelPipeline.addLast("command-encoder", new CommandEncoder(true));

                // and then business logic.
                channelPipeline.addLast("handler", new OFTPClientHandler());
                break;
        }
    }
}
