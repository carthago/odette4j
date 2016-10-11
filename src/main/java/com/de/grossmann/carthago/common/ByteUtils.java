package com.de.grossmann.carthago.common;

import java.io.File;

public final class ByteUtils {

    private static final String HEXES = "0123456789ABCDEF";

    private ByteUtils() {
        // Utility classes should not have a public or default constructor
    }

    public static byte[] byteToHalfBytes(final byte aByte) {
        return new byte[]{(byte) ((aByte & 0xFF) >>> 4), (byte) (aByte & 0x0F)};
    }

    public static byte halfBytesToByte(final byte highByte, final byte lowByte) {
        return (byte) ((highByte << 4) | (lowByte & 0x0F));
    }

    public static String byteToHex(final byte aByte) {
        return String.format("0x%02X", aByte);
    }

    public static String byteToBin(final byte aByte) {
        StringBuilder sb = new StringBuilder("00000000");
        for (int bit = 0; bit < 8; bit++) {
            if (((aByte >> bit) & 1) > 0) {
                sb.setCharAt(7 - bit, '1');
            }
        }
        return sb.toString();
    }

    public static String halfByteToBin(final byte aByte) {
        StringBuilder sb = new StringBuilder("0000");
        for (int bit = 0; bit < 8; bit++) {
            if (((aByte >> bit) & 1) > 0) {
                sb.setCharAt(3 - bit, '1');
            }
        }
        return sb.toString();
    }

    public static byte[] intToByteArray(final int intValue) {
        return new byte[]{(byte) (intValue >>> 24), (byte) (intValue >>> 16),
                (byte) (intValue >>> 8), (byte) intValue};
    }

    public static int byteArrayToInt(final byte[] bytes) {
        return (bytes[0] << 24) + ((bytes[1] & 0xFF) << 16) + ((bytes[2] & 0xFF) << 8)
                + (bytes[3] & 0xFF);
    }

    public static String bytesToHex(final byte[] bytes, final int offset ) {

        StringBuffer sHex  = new StringBuffer( 86 );
        StringBuffer sChar = new StringBuffer( 32 );
        StringBuffer sLog  = new StringBuffer( 118 );

        String s;
        String sh;

        int position = 0;
        int cursor = 0;
        int myByte;

        while ( position < bytes.length)
        {
            if (bytes[position] < 0)
            {
                myByte = 256 + bytes[position];
            }
            else
            {
                myByte = bytes[position];
            }

            s =  Integer.toHexString(myByte).toUpperCase();
            if ( s.length() == 1)
            {
                sh = "0"+s;
            }
            else
            {
                sh = s;
            }

            sHex.append(sh);
            cursor += 2;

            if ( ((position + 1) % 8) == 0 && ((position +1) % 32 != 0) && ((position +1) != bytes.length))
            {
                sHex.append(" | ");
                cursor += 3;
            }
            else if ( (position + 1 ) % 2 == 0)
            {
                sHex.append(" ");
                cursor += 1;
            }


            if (Character.isLetterOrDigit((char)bytes[position]))
            {
                sChar.append((char)bytes[position]);
            }
            else
            {
                sChar.append(".");
            }

            if (((position +1) % 32) == 0 || (position + 1) == bytes.length)
            {
                if((position + 1) > 32)
                {
                    sLog.append(String.format("%-"+ offset + "s%s", "",sHex));
                } else {
                    sLog.append(sHex);
                }

                int spaces = 86 - cursor;

                for(int i = spaces; i > 0; i--)
                {
                    sLog.append(" ");
                }

                sLog.append(sChar);
                sLog.append("\n");

                cursor = 0;

                sHex  = new StringBuffer( 86 );
                sChar = new StringBuffer( 32 );

            }
            position++;
        }

        return sLog.toString();
    }
}