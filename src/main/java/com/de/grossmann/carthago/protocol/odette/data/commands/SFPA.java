package com.de.grossmann.carthago.protocol.odette.data.commands;

import com.de.grossmann.carthago.protocol.odette.data.OFTPType;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType.Field;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType.Format;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType.Type;

/**
 * o-------------------------------------------------------------------o
 * |       SFPA        Start File Positive Answer                      |
 * |                                                                   |
 * |       Start File Phase           Speaker <---- Listener           |
 * |-------------------------------------------------------------------|
 * | Pos | Field     | Description                           | Format  |
 * |-----+-----------+---------------------------------------+---------|
 * |   0 | SFPACMD   | SFPA Command, '2'                     | F X(1)  |
 * |   1 | SFPAACNT  | Answer Count                          | V 9(17) |
 * o-------------------------------------------------------------------o
 */
public final class SFPA
        extends Command {

    /**
     * Command Code Character.<br>
     * '2' SFPA Command identifier.
     */
    @OFTPType(position = 0, field = Field.SFPACMD, format = Format.F, type = Type.X, length = 1)
    private CommandIdentifier sfpacmd;

    /**
     *  Answer Count<br>
     *
     * The Listener must enter a count lower than or equal to the<br>
     * restart count specified by the Speaker in the Start File<br>
     * (SFID) command.  The count expresses the received user<br>
     * data.  If restart facilities are not available, a count of<br>
     * zero must be specified.<br>
     */
    @OFTPType(position = 1, field = Field.SFPAACNT, format = Format.V, type = Type._9, length = 17)
    private Long sfpacnt;

    public SFPA() {
        this.sfpacmd = CommandIdentifier.SFPA;
    }

    public SFPA(final byte[] byteArray) {
        super.setBytes(byteArray);
    }

    public Long getSfpacnt() {
        return sfpacnt;
    }

    public void setSfpacnt(Long sfpacnt) {
        this.sfpacnt = sfpacnt;
    }
}
