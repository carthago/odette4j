package com.de.grossmann.carthago;

import com.de.grossmann.carthago.protocol.odette.OFTPClient;
import com.de.grossmann.carthago.protocol.odette.codec.Transport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
