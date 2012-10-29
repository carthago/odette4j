package com.de.grossmann.carthago.protocol.odette;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.de.grossmann.carthago.protocol.odette.codec.data.command.Command;

public class OFTPClientHandler extends ChannelInboundMessageHandlerAdapter<Command>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(OFTPClientHandler.class);

    @Override
    public void messageReceived(final ChannelHandlerContext channelHandlerContext, final Command command) throws Exception
    {
        LOGGER.debug("RCV: " + command);
        LOGGER.debug("DUMMY: doing some magic");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.warn("Unexpected exception from downstream.", cause);
        ctx.close();
    }
}
