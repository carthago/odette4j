package com.de.grossmann.carthago.protocol.odette;

import com.de.grossmann.carthago.protocol.odette.data.commands.SSID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.de.grossmann.carthago.protocol.odette.data.commands.Command;

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
          SSID ssid = new SSID();
          ssid.setSsidlev(5L);
          ssid.setSsidcode("OEVOMIGWIN");
          ssid.setSsidpswd("111111");
          ssid.setSsidsdeb(99999L);
          ssid.setSsidsr("B");
          ssid.setSsidcmpr("N");
          ssid.setSsidrest("N");
          ssid.setSsidspec("N");
          ssid.setSsidcred(999L);
          ssid.setSsidauth("N");
          ssid.setSsidrsv1("");
          ssid.setSsiduser("");
          ssid.setSsidcr("\r");
      }
    }
}
