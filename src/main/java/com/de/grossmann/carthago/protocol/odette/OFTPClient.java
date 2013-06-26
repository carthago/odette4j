package com.de.grossmann.carthago.protocol.odette;

import com.de.grossmann.carthago.protocol.odette.codec.Transport;
import com.de.grossmann.carthago.protocol.odette.data.command.Command;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OFTPClient {

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(OFTPClient.class);
    }

    private final String host;
    private final int port;
    private final Bootstrap bootstrap;
    private Channel channel;
    private ChannelFuture writeFuture;

    public OFTPClient(final String host, final int port, final Transport transport) {

        this.host = host;
        this.port = port;
        this.bootstrap = new Bootstrap();

        this.bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .remoteAddress(host, port)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_LINGER,0)
                .handler(new OFTPClientInitializer(transport));
    }

    public void connect() {
        try {
            this.channel = this.bootstrap.connect().syncUninterruptibly().channel();
        } catch (Exception e) {
            LOGGER.warn("Unable to connect to " + this.host + ":" + this.port + ". Reason = " + e.getMessage());
            this.bootstrap.shutdown();
        }
    }

    public boolean isConnected() {
        boolean connected = false;

        if (this.channel != null) {
            connected = this.channel.isActive();
        }

        return connected;
    }

    public void disconnect() {
        // Wait until all messages are flushed before closing the channel.
        if (this.writeFuture != null) {
            try {
                this.writeFuture.sync();
            } catch (InterruptedException e) {
                LOGGER.error("Unable to connect to " + this.bootstrap, e);
            }
        }
        this.bootstrap.shutdown();
    }

    public void send(final Command command) {
        if (this.channel != null && this.channel.isActive()) {
            this.writeFuture = channel.write(command);
        } else {
            // TODO throw Exception
            LOGGER.error("Unable to send command. Not connected.");
        }
    }
}
