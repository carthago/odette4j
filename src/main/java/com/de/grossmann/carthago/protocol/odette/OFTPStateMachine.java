package com.de.grossmann.carthago.protocol.odette;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.de.grossmann.carthago.protocol.odette.data.command.Command;

import akka.actor.UntypedActor;

public class OFTPStateMachine extends UntypedActor
{
    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(OFTPStateMachine.class);
    }
    
    public void onReceive(Object message) throws Exception {
      if (message instanceof Command)
      {
        LOGGER.info("RCV: " + ((Command) message));
      }
    }
}
