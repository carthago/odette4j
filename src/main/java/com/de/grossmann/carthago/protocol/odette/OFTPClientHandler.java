package com.de.grossmann.carthago.protocol.odette;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.de.grossmann.carthago.protocol.odette.codec.data.OdetteExchangeBuffer;

public class OFTPClientHandler extends ChannelInboundMessageHandlerAdapter<OdetteExchangeBuffer>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(OFTPClientHandler.class);

    @Override
    public void messageReceived(final ChannelHandlerContext channelHandlerContext, final OdetteExchangeBuffer oeb) throws Exception
    {
        LOGGER.debug(oeb.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        LOGGER.warn("Unexpected exception from downstream.", cause);
        ctx.close();
    }
}
