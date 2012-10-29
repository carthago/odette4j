/**
 * 8.2.  Stream Transmission Header Format
 *
 *    The Stream Transmission Header is shown below.  The fields are
 *    transmitted from left to right.
 *
 *     0                   1                   2                   3
 *     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *    |Version| Flags | Length                                        |
 *    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *
 *
 *    Version
 *
 *       Value: 0001 (binary)
 *
 *              Stream Transmission Header version number.
 *
 *    Flags
 *
 *       Value: 0000 (binary)
 *
 *              Reserved for future use.
 *
 *    Length
 *
 *       Range: 5 - 100003 (decimal)
 *
 *       The length of the Stream Transmission Buffer (STH+OEB).
 *
 *       The smallest STB is 5 octets consisting of a 4-octet header
 *       followed by a 1-octet Exchange Buffer such as a Change Direction
 *       (CD) command.
 *
 *       The maximum Exchange Buffer length that can be negotiated is 99999
 *       octets (Section 5.3.2) giving an STB length of 100003.
 *
 *       The length is expressed as a binary number in network byte order.
 *
 *    It is expected that implementations of this protocol will follow the
 *    Internet robustness principle of being conservative in what is sent
 *    and liberal in what is accepted.
 *
 */
package com.de.grossmann.carthago.protocol.odette.codec.data;

import static com.de.grossmann.carthago.common.ByteUtils.halfByteToBin;
import static com.de.grossmann.carthago.common.ByteUtils.halfBytesToByte;

public class StreamTransmissionHeader {
    public static final short STH_LENGTH = 4;
    private static final byte STH_VERSION = 0x01;
    private static final byte STH_FLAGS = 0x00;

    private final byte version;
    private final byte flags;
    private final int length;

    public StreamTransmissionHeader(int length) {
        this.version = STH_VERSION;
        this.flags = STH_FLAGS;
        this.length = STH_LENGTH + length;
    }

    public StreamTransmissionHeader(byte version, byte flags, int length) {
        this.version = version;
        this.flags = flags;
        this.length = length;
    }

    public byte getVersion() {
        return this.version;
    }

    public byte getFlags() {
        return this.flags;
    }

    public int getLength() {
        return this.length;
    }

    public boolean isValid() {
        return (this.version == STH_VERSION) && (this.flags == STH_FLAGS)
                && (this.length >= 5 && this.length <= 100003);
    }

    public byte[] getBytes() {
        return new byte[]{halfBytesToByte(this.version, this.flags), (byte) (this.length >>> 16),
                (byte) (this.length >>> 8), (byte) (this.length)};
    }

    @Override
    public String toString() {
        return String.format("STH [version=%s, flags=%s, length=%s]", halfByteToBin(this.version),
                halfByteToBin(this.flags), this.length);
    }
}