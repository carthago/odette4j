package com.de.grossmann.carthago.protocol.odette.codec.data;

import static com.de.grossmann.carthago.common.ByteUtils.bytesToHex;

public class OdetteExchangeBuffer {

    private final byte[] payload;

    public OdetteExchangeBuffer(byte[] payload) {
        this.payload = payload;
    }

    public char getCommand() {
        return (char) payload[0];
    }

    public byte[] getBytes() {
        return this.payload;
    }

    public int getLength() {
        return this.payload.length;
    }

    @Override
    public String toString() {
        return String.format("OEB [command=%s, payload=%s]", getCommand(), bytesToHex(getBytes()));
    }
}