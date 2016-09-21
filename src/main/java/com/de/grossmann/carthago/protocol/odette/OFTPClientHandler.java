package com.de.grossmann.carthago.protocol.odette;

import com.de.grossmann.carthago.protocol.odette.config.OFTPSessionConfiguration;
import com.de.grossmann.carthago.protocol.odette.data.commands.*;
import io.netty.channel.ChannelHandlerContext;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class OFTPClientHandler extends ChannelInboundHandlerAdapter
{

    private static final Logger LOGGER;

    private final OFTPSessionConfiguration oftpSessionConfigurationDefaults;

    private final static AttributeKey<Long>                     DATA_PDU_COUNTER_KEY          = AttributeKey
        .newInstance("receivedDataPdus");
    private final static AttributeKey<OFTPSessionConfiguration> OFTP_SESSION_CONFIGURTION_KEY = AttributeKey
        .newInstance("oftpSessionConfiguration");
    private final static AttributeKey<Long>                     TIMER_KEY                     = AttributeKey
        .newInstance("timer");

    static {
        LOGGER = LoggerFactory.getLogger(OFTPClientHandler.class);
    }

    OFTPClientHandler(final OFTPSessionConfiguration oftpSessionConfiguration) {
        this.oftpSessionConfigurationDefaults = oftpSessionConfiguration;
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
        LOGGER.debug("Channel {} activated. {} -> {}", ctx.channel().id(), ctx.channel().localAddress(),
                     ctx.channel().remoteAddress());
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

        if (command instanceof SSRM) {
            response = new SSID();
            ((SSID)response).setSsidlev(this.oftpSessionConfigurationDefaults.getLevel());
            ((SSID)response).setSsidcode(this.oftpSessionConfigurationDefaults.getUserCode());
            ((SSID)response).setSsidpswd(this.oftpSessionConfigurationDefaults.getPassword());
            ((SSID)response).setSsidsdeb(this.oftpSessionConfigurationDefaults.getDataExchangeBufferSize());
            ((SSID)response).setSsidsr(String.valueOf(this.oftpSessionConfigurationDefaults.getCapabilities()));
            ((SSID)response).setSsidcmpr(String.valueOf(this.oftpSessionConfigurationDefaults.getCompression()));
            ((SSID)response).setSsidrest(String.valueOf(this.oftpSessionConfigurationDefaults.getRestart()));
            ((SSID)response).setSsidspec(String.valueOf(this.oftpSessionConfigurationDefaults.getSpecialLogic()));
            ((SSID)response).setSsidcred(this.oftpSessionConfigurationDefaults.getCredit());
            ((SSID)response).setSsidauth(String.valueOf(this.oftpSessionConfigurationDefaults.getAuthentication()));
            ((SSID)response).setSsidrsv1(this.oftpSessionConfigurationDefaults.getReserved());
            ((SSID)response).setSsiduser(this.oftpSessionConfigurationDefaults.getUserData());
        }
        else if (command instanceof SSID) {
            response = new SFID();
            //((SFID)response).s
        }
        else if (command instanceof ESID) {
            ctx.close();
        }

        return response;
    }
}
