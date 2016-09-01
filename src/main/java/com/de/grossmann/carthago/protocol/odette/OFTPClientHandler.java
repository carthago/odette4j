package com.de.grossmann.carthago.protocol.odette;

import com.de.grossmann.carthago.protocol.odette.data.commands.Command;
import com.de.grossmann.carthago.protocol.odette.data.commands.ESID;
import com.de.grossmann.carthago.protocol.odette.data.commands.SSID;
import com.de.grossmann.carthago.protocol.odette.data.commands.SSRM;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OFTPClientHandler extends ChannelHandlerAdapter
{

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(OFTPClientHandler.class);
    }

    /**
     * Calls {@link io.netty.channel.ChannelHandlerContext#fireChannelActive()} to forward
     * to the next {@link io.netty.channel.ChannelHandler} in the {@link io.netty.channel.ChannelPipeline}.
     * <p/>
     *
     * @param ctx the {@link io.netty.channel.ChannelHandlerContext} which this {@link io.netty.handler.codec.ByteToMessageDecoder} belongs to
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        LOGGER.debug("Channel {} activated. {} -> {}", new Object[]{ctx.channel().id(), ctx.channel().localAddress(), ctx.channel().remoteAddress()});
        super.channelActive(ctx);
    }

    /**
     * Calls {@link io.netty.channel.ChannelHandlerContext#fireChannelRead(Object)} to forward
     * to the next {@link io.netty.channel.ChannelHandler} in the {@link io.netty.channel.ChannelPipeline}.
     * <p/>
     *
     * @param ctx the {@link io.netty.channel.ChannelHandlerContext} which this {@link io.netty.handler.codec.ByteToMessageDecoder} belongs to
     * @param msg the {@link java.lang.Object} which holds the decoded message
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        Command response = handleCommand(ctx, (Command) msg);
        if (response != null) {
            send(ctx, response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.warn("Unexpected exception from downstream. " + cause.getMessage());
        ctx.close();
    }

    private void send(final ChannelHandlerContext ctx, final Command command){
        ctx.write(command);
        ctx.flush();
    }

    private Command handleCommand(final ChannelHandlerContext ctx, final Command command) {
        Command response = null;

        if (command instanceof SSRM)
        {
            response = new SSID();
            ((SSID)response).setSsidlev(5L);
            ((SSID)response).setSsidcode("ODEVLAB2");
            ((SSID)response).setSsidpswd("111111");
            ((SSID)response).setSsidsdeb(99999L);
            ((SSID)response).setSsidsr("B");
            ((SSID)response).setSsidcmpr("N");
            ((SSID)response).setSsidrest("N");
            ((SSID)response).setSsidspec("N");
            ((SSID)response).setSsidcred(999L);
            ((SSID)response).setSsidauth("N");
            ((SSID)response).setSsidrsv1("");
            ((SSID)response).setSsiduser("");

        } else if (command instanceof ESID) {
            ctx.close();
        }

        return response;
    }
}
