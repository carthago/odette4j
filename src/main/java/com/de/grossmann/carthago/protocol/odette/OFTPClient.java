package com.de.grossmann.carthago.protocol.odette;

import com.de.grossmann.carthago.protocol.odette.codec.Transport;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OFTPClient {

    private final String host;
    private final int port;
    private final Transport transport;


    public OFTPClient(final String host, final int port, final Transport transport) {
        this.host = host;
        this.port = port;
        this.transport = transport;
    }

    public void run() throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .remoteAddress(host, port)
                    .handler(new OFTPClientInitializer(this.transport));

            // Start the connection attempt.
            Channel channel = bootstrap.connect().sync().channel();

            // Read commands from the stdin.
            ChannelFuture lastWriteFuture = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for (; ; ) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }

                // Sends the received line to the server.
                lastWriteFuture = channel.write(line + "\r\n");

                // If user typed the 'bye' command, wait until the server closes
                // the connection.
                if (line.toLowerCase().equals("bye")) {
                    channel.closeFuture().sync();
                    break;
                }
            }

            // Wait until all messages are flushed before closing the channel.
            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }
        } finally {
            // The connection is closed automatically on shutdown.
            bootstrap.shutdown();
        }
    }
}
