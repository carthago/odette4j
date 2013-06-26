package com.de.grossmann.carthago.protocol.odette;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.de.grossmann.carthago.protocol.odette.data.command.Command;

import com.de.grossmann.carthago.protocol.odette.data.command.SSID;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OFTPClientHandler extends ChannelInboundMessageHandlerAdapter<Command> {

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(OFTPClientHandler.class);
    }

    @Override
    public void messageReceived(final ChannelHandlerContext channelHandlerContext, final Command command) throws Exception {
        //LOGGER.debug("RCV: " + command);
        
        ActorSystem protocolAdapter = ActorSystem.create("ProtocolAdapter");
        ActorRef commandSender = protocolAdapter.actorOf(new Props(OFTPStateMachine.class));
        commandSender.tell(command,commandSender);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.warn("Unexpected exception from downstream. " + cause.getMessage());
        ctx.close();
    }
}
