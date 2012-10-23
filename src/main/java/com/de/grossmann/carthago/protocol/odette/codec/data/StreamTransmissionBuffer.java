package com.de.grossmann.carthago.protocol.odette.codec.data;

public class StreamTransmissionBuffer {
    public static final int MIN_STB_SIZE = 5;
    public static final int MAX_STB_SIZE = 100003;

    private final StreamTransmissionHeader sth;
    private final OdetteExchangeBuffer oeb;

    public StreamTransmissionBuffer(StreamTransmissionHeader sth, OdetteExchangeBuffer oeb) {
        this.sth = sth;
        this.oeb = oeb;
    }

    public StreamTransmissionHeader getSTH() {
        return sth;
    }

    public OdetteExchangeBuffer getOEB() {
        return oeb;
    }

    public boolean isValid() {
        return (this.sth.getLength() == this.oeb.getLength() + StreamTransmissionHeader.STH_LENGTH)
                && (this.sth.getLength() >= MIN_STB_SIZE && this.sth.getLength() <= MAX_STB_SIZE);
    }

    @Override
    public String toString() {
        return String.format("StreamTransmissionBuffer [%s, %s]", sth, oeb);
    }
}