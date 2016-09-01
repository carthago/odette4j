package com.de.grossmann.carthago.protocol.odette.config;

import com.de.grossmann.carthago.protocol.odette.codec.Transport;

public class OFTPNetworkConfiguration
{

    private final String    host;
    private final Integer   port;
    private final Transport transport;

    public OFTPNetworkConfiguration(final String host, final Integer port, final Transport transport)
    {

        this.host = host;
        this.port = port;
        this.transport = transport;

    }

    public String getHost()
    {
        return host;
    }

    public Integer getPort()
    {
        return port;
    }

    public Transport getTransport()
    {
        return transport;
    }
}
