package com.de.grossmann.carthago.common;

public final class ByteUtils
{

    private static final String HEXES = "0123456789ABCDEF";

    private ByteUtils()
    {
        // Utility classes should not have a public or default constructor
    }

    public static byte[] byteToHalfBytes(final byte aByte)
    {
        return new byte[]{(byte) ((aByte & 0xFF) >>> 4), (byte) (aByte & 0x0F)};
    }

    public static byte halfBytesToByte(byte highByte, byte lowByte)
    {
        return (byte) ((highByte << 4) | (lowByte & 0x0F));
    }

    public static String byteToHex(byte aByte)
    {
        return String.format("0x%02X", aByte);
    }

    public static String byteToBin(byte aByte)
    {
        StringBuilder sb = new StringBuilder("00000000");
        for (int bit = 0; bit < 8; bit++)
        {
            if (((aByte >> bit) & 1) > 0)
            {
                sb.setCharAt(7 - bit, '1');
            }
        }
        return sb.toString();
    }

    public static String halfByteToBin(byte aByte)
    {
        StringBuilder sb = new StringBuilder("0000");
        for (int bit = 0; bit < 8; bit++)
        {
            if (((aByte >> bit) & 1) > 0)
            {
                sb.setCharAt(3 - bit, '1');
            }
        }
        return sb.toString();
    }

    public static byte[] intToByteArray(final int intValue)
    {
        return new byte[]{(byte) (intValue >>> 24), (byte) (intValue >>> 16),
                          (byte) (intValue >>> 8), (byte) intValue};
    }

    public static int byteArrayToInt(final byte[] bytes)
    {
        return (bytes[0] << 24) + ((bytes[1] & 0xFF) << 16) + ((bytes[2] & 0xFF) << 8)
               + (bytes[3] & 0xFF);
    }

    public static String bytesToHex(final byte[] bytes)
    {
        final StringBuilder hex = new StringBuilder(2 * bytes.length);
        for (final byte b : bytes)
        {
            hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F))).append(" ");
        }
        return hex.toString();
    }

}