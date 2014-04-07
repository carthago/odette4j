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

        OFTPClient client = new OFTPClient("MyTestServer", 3305, Transport.TCP);


    }
}
