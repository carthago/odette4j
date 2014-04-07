package com.de.grossmann.carthago;

import com.de.grossmann.carthago.protocol.odette.OFTPClient;
import com.de.grossmann.carthago.protocol.odette.codec.Transport;
import com.de.grossmann.carthago.protocol.odette.data.command.SSID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class App {

    private final static Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(App.class);
    }

    public static void main(String[] args) throws Exception {
        LOGGER.debug("<START>");
        OFTPClient client = new OFTPClient("rvsblade5", 3305, Transport.TCP);

//        SSID ssid = new SSID();
//        ssid.setSsidlev(5L);
//        ssid.setSsidcode("OEVOMIGWIN");
//        ssid.setSsidpswd("111111");
//        ssid.setSsidsdeb(99999L);
//        ssid.setSsidsr("B");
//        ssid.setSsidcmpr("N");
//        ssid.setSsidrest("N");
//        ssid.setSsidspec("N");
//        ssid.setSsidcred(999L);
//        ssid.setSsidauth("N");
//        ssid.setSsidrsv1("");
//        ssid.setSsiduser("");
//        ssid.setSsidcr("\r");
//
//        LOGGER.info("SND: " + ssid);
//        client.send(ssid);
    }
}
