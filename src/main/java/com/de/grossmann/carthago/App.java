package com.de.grossmann.carthago;

import com.de.grossmann.carthago.protocol.odette.OFTPClient;
import com.de.grossmann.carthago.protocol.odette.OFTPServer;
import com.de.grossmann.carthago.protocol.odette.codec.Transport;
import com.de.grossmann.carthago.protocol.odette.config.OFTPNetworkConfiguration;
import com.de.grossmann.carthago.protocol.odette.config.OFTPSessionConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App {

    private final static Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(App.class);
    }

    public static void main(String[] args) throws Exception {
        LOGGER.debug("<START>");

        OFTPNetworkConfiguration oftpNetworkConfiguration = new OFTPNetworkConfiguration("RDDE9CMX", 3306, Transport.TCP);
        OFTPSessionConfiguration oftpSessionConfiguration = new OFTPSessionConfiguration("O01472583424321", "111111");
        oftpSessionConfiguration.setDataExchangeBufferSize(8192L);
        oftpSessionConfiguration.setCredit(100L);

        new OFTPServer(oftpNetworkConfiguration, oftpSessionConfiguration);

    }

}
