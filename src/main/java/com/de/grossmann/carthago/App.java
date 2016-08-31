package com.de.grossmann.carthago;

import com.de.grossmann.carthago.protocol.odette.OFTPClient;
import com.de.grossmann.carthago.protocol.odette.OFTPServer;
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

        OFTPServer server = new OFTPServer("10.0.0.38", 3305, Transport.TCP);
        //OFTPClient client = new OFTPClient("nebulos.mirko-wodtke.de", 3305, Transport.TCP);

    }

}
