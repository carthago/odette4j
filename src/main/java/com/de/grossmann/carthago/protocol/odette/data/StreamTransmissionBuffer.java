package com.de.grossmann.carthago.protocol.odette.data;

import java.util.Arrays;

public class StreamTransmissionBuffer {
    public static final int MIN_SIZE = 5;
    public static final int MAX_SIZE = 100003;

    private final StreamTransmissionHeader streamTransmissionHeader;
    private final byte[] odetteExchangeBuffer;

    public StreamTransmissionBuffer(StreamTransmissionHeader streamTransmissionHeader, byte[] odetteExchangeBuffer) {
        this.streamTransmissionHeader = streamTransmissionHeader;
        this.odetteExchangeBuffer = odetteExchangeBuffer;
    }

    public StreamTransmissionHeader getStreamTransmissionHeader() {
        return streamTransmissionHeader;
    }

    public byte[] getOdetteExchangeBuffer() {
        return odetteExchangeBuffer;
    }

    public boolean isValid() {
        return (getStreamTransmissionHeader().getLength() == getOdetteExchangeBuffer().length + StreamTransmissionHeader.STH_LENGTH)
                && (getStreamTransmissionHeader().getLength() >= MIN_SIZE && getStreamTransmissionHeader().getLength() <= MAX_SIZE);
    }

    @Override
    public String toString() {
        return String.format("StreamTransmissionBuffer [%s, %s]", getStreamTransmissionHeader(), Arrays.toString(getOdetteExchangeBuffer()));
    }
}