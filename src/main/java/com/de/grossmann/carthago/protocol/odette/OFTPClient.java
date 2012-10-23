package com.de.grossmann.carthago.protocol.odette;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.de.grossmann.carthago.protocol.odette.codec.Transport;

public class OFTPClient
{

    private final String    host;
    private final int       port;
    private final Transport transport;
    

    public OFTPClient(final String host, final int port, final Transport transport)
    {
        this.host      = host;
        this.port      = port;
        this.transport = transport;
    }

    public void run() throws Exception
    {
        Bootstrap b = new Bootstrap();
        try
        {
            b.group(new NioEventLoopGroup())
             .channel(NioSocketChannel.class)
             .remoteAddress(host, port)
             .handler(new OFTPClientInitializer(this.transport));

            // Start the connection attempt.
            Channel ch = b.connect().sync().channel();

            // Read commands from the stdin.
            ChannelFuture lastWriteFuture = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for (; ; )
            {
                String line = in.readLine();
                if (line == null)
                {
                    break;
                }

                // Sends the received line to the server.
                lastWriteFuture = ch.write(line + "\r\n");

                // If user typed the 'bye' command, wait until the server closes
                // the connection.
                if (line.toLowerCase().equals("bye"))
                {
                    ch.closeFuture().sync();
                    break;
                }
            }

            // Wait until all messages are flushed before closing the channel.
            if (lastWriteFuture != null)
            {
                lastWriteFuture.sync();
            }
        } finally
        {
            // The connection is closed automatically on shutdown.
            b.shutdown();
        }
    }
}
