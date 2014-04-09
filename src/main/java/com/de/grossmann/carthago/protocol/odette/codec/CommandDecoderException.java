package com.de.grossmann.carthago.protocol.odette.codec;

public class CommandDecoderException extends Exception {

    private static final long serialVersionUID = 2776672062956289603L;

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public CommandDecoderException(String message)
    {
        super(message);
    }
}
