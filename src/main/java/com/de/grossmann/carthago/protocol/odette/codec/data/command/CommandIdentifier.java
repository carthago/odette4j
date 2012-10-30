package com.de.grossmann.carthago.protocol.odette.codec.data.command;


import java.lang.reflect.InvocationTargetException;

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
    
    public static CommandIdentifier valueOf(final char identifier) {
        CommandIdentifier newCommandIdentifier = null;
        
        for (CommandIdentifier commandIdentifier : values()) {
            if (commandIdentifier.getIdentifier() == identifier) {
                newCommandIdentifier = commandIdentifier;
            }
        }
        return newCommandIdentifier;
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
                    command  = (Command)commandIdentifier.getImplementation().getConstructor(byte[].class).newInstance(odetteExchangeBuffer);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
                    LOGGER.error("Unable to create command from Odette Exchange Buffer.", e);
                }
            }
        }
        return command;
    }
}
