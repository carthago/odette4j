package com.de.grossmann.carthago.protocol.odette.codec.data.command;


public enum CommandIdentifier {

    SSRM('I', SSRM.class),
    SSID('X', SSID.class);

    private char identifier;
    private Class<?> implementation;
    
    private CommandIdentifier(final char identifier, final Class<?> implementation)
    {
        this.identifier = identifier;
        this.implementation = implementation;
    }
    
    public char getIdentifier() {
        return this.identifier;
    }
    
    public Class<?> getImplementation() {
        return this.implementation;
    }
    
    public static Command identifyCommand(final byte[] odetteExchangeBuffer) {
        Command command = null;
        
        for (CommandIdentifier commandIdentifier : values()) {
            if (commandIdentifier.getIdentifier() == (char)odetteExchangeBuffer[0]) {
                try
                {
                    command = (Command) commandIdentifier.getImplementation().newInstance();
                    command.initialize(odetteExchangeBuffer);
                }
                catch (InstantiationException e)
                {
                    System.err.println("FOO: " + e.getMessage());
                }
                catch (IllegalAccessException e)
                {
                    System.err.println("FOO: " + e.getMessage());
                }
            }
        }
        return command;
    }
}
