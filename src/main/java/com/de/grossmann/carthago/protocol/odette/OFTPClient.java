package com.de.grossmann.carthago.protocol.odette;

import com.de.grossmann.carthago.protocol.odette.codec.Transport;
import com.de.grossmann.carthago.protocol.odette.config.OFTPNetworkConfiguration;
import com.de.grossmann.carthago.protocol.odette.config.OFTPSessionConfiguration;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OFTPClient {

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(OFTPClient.class);
    }
;

    public OFTPClient(final OFTPNetworkConfiguration oftpNetworkConfiguration,
                      final OFTPSessionConfiguration oftpSessionConfiguration) {

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try
        {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(oftpNetworkConfiguration.getHost(), oftpNetworkConfiguration.getPort())
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_LINGER, 0)
                    .handler(new OFTPClientInitializer(oftpNetworkConfiguration, oftpSessionConfiguration));

            // Start the client.
            ChannelFuture f = bootstrap.connect().sync(); // (5)

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        }
        catch (InterruptedException e)
        {
            LOGGER.error("Place some useful error message here.", e);
        }
        finally {
            workerGroup.shutdownGracefully();
        }
    }
}
