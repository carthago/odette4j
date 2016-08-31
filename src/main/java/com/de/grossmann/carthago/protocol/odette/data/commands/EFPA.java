package com.de.grossmann.carthago.protocol.odette.data.commands;

import com.de.grossmann.carthago.protocol.odette.data.OFTPType;

/**
 * o-------------------------------------------------------------------o
 * |       EFPA        End File Positive Answer                        |
 * |                                                                   |
 * |       End File Phase             Speaker <---- Listener           |
 * |-------------------------------------------------------------------|
 * | Pos | Field     | Description                           | Format  |
 * |-----+-----------+---------------------------------------+---------|
 * |   0 | EFPACMD   | EFPA Command, '4'                     | F X(1)  |
 * |   1 | EFPACD    | Change Direction Indicator, (Y/N)     | F X(1)  |
 * o-------------------------------------------------------------------o
 */
public final class EFPA
        extends Command {

    /**
     * Command Code Character.<br>
     * '4' EFPA Command identifier.
     */
    @OFTPType(position = 0, field = OFTPType.Field.EFPACMD, format = OFTPType.Format.F, type = OFTPType.Type.X, length = 1)
    private CommandIdentifier efpacmd;

    /**
     *  Change Direction Indicator, (Y/N) <br>
     */
    @OFTPType(position = 1, field = OFTPType.Field.EFPACD, format = OFTPType.Format.F, type = OFTPType.Type.X, length = 1)
    private String efpacd;


    public EFPA() {
        this.efpacmd = CommandIdentifier.EFPA;
    }

    public EFPA(final byte[] byteArray) {
        super.setBytes(byteArray);
    }

    public String getEfpacd() {
        return efpacd;
    }

    public void setEfpacd(String efpacd) {
        this.efpacd = efpacd;
    }
}
