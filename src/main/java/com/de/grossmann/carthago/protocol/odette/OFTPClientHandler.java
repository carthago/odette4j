package com.de.grossmann.carthago.protocol.odette;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OFTPClientHandler extends ChannelInboundMessageHandlerAdapter
{
    private static final Logger LOGGER = LoggerFactory.getLogger(OFTPClientHandler.class);

    @Override
    public void messageReceived(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception
    {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        LOGGER.warn("Unexpected exception from downstream.", cause);
        ctx.close();
    }


}
