package com.de.grossmann.carthago.protocol.odette.codec.data.command;


public interface Command {

    CommandIdentifier getCommandIdentifier();
    void initialize(byte[] odetteExchangeBuffer);
    byte[] getBytes();
    int getLength();
    
}
