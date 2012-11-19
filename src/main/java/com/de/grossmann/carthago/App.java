package com.de.grossmann.carthago;

import com.de.grossmann.carthago.protocol.odette.OFTPClient;
import com.de.grossmann.carthago.protocol.odette.codec.Transport;
import com.de.grossmann.carthago.protocol.odette.data.command.SSRM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private final static Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(App.class);
    }

    public static void main(String[] args) throws Exception {
        LOGGER.debug("<START>");
        OFTPClient client = new OFTPClient("192.168.56.101", 3305, Transport.TCP);
        client.connect();
        LOGGER.debug(client.isConnected() ? "<CONNECTED>" : "<UNCONNECTED>");
        
        

       
    }
}
