package com.de.grossmann.carthago.protocol.odette.codec.data.command;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum CommandIdentifier {

    SSRM('I', SSRM.class),
    SSID('X', SSID.class);

    private final static Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(CommandIdentifier.class);
    }

    private final char identifier;
    private final Class<?> implementation;

    private CommandIdentifier(final char identifier, final Class<?> implementation) {
        this.identifier = identifier;
        this.implementation = implementation;
    }

    public char getIdentifier() {
        return this.identifier;
    }
    
    @Override    
    public String toString() {
        return new String(new char[]{this.identifier});
    }
    
    public Class<?> getImplementation() {
        return this.implementation;
    }

    public static Command identifyCommand(final byte[] odetteExchangeBuffer) {
        Command command = null;

        for (CommandIdentifier commandIdentifier : values()) {
            if (commandIdentifier.getIdentifier() == (char) odetteExchangeBuffer[0]) {
                try {
                    // TODO einen Weg finden, wie wir constructor args übergeben können
                    command = (Command) commandIdentifier.getImplementation().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    LOGGER.error("Unable to create command from Odette Exchange Buffer.", e);
                }
            }
        }
        return command;
    }
}
