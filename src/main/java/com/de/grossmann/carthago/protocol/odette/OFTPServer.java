package com.de.grossmann.carthago.protocol.odette;

import com.de.grossmann.carthago.protocol.odette.config.OFTPNetworkConfiguration;
import com.de.grossmann.carthago.protocol.odette.config.OFTPSessionConfiguration;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OFTPServer
{

    private static final Logger LOGGER;

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final ChannelFuture  channelFuture;

    static
    {
        LOGGER = LoggerFactory.getLogger(OFTPServer.class);
    }

    public OFTPServer(final OFTPNetworkConfiguration oftpNetworkConfiguration,
                      final OFTPSessionConfiguration oftpSessionConfiguration) throws Exception
    {

        // Configure the server.
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        try
        {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                           .option(ChannelOption.SO_BACKLOG, 100).handler(new LoggingHandler(LogLevel.INFO))
                           .childHandler(new OFTPServerInitializer(oftpNetworkConfiguration, oftpSessionConfiguration));

            // Start the server.
            channelFuture = serverBootstrap
                .bind(oftpNetworkConfiguration.getHost(), oftpNetworkConfiguration.getPort()).sync();

            // Wait until the server socket is closed.
            channelFuture.channel().closeFuture().sync();
        }
        finally
        {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
