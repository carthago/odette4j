package com.de.grossmann.carthago.protocol.odette.data.commands;

import com.de.grossmann.carthago.protocol.odette.data.OFTPType;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType.Field;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType.Format;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType.Type;

/**
 * o-------------------------------------------------------------------o
 * |       DATA        Data Exchange Buffer                            |
 * |                                                                   |
 * |       Data Transfer Phase        Speaker ----> Listener           |
 * |-------------------------------------------------------------------|
 * | Pos | Field     | Description                           | Format  |
 * |-----+-----------+---------------------------------------+---------|
 * |   0 | DATACMD   | DATA Command, 'D'                     | F X(1)  |
 * |   1 | DATABUF   | Data Exchange Buffer payload          | V U(n)  |
 * o-------------------------------------------------------------------o
 */
public final class DATA
        extends Command {

    /**
     * Command Code Character.<br>
     * '2' DATA Command identifier.
     */
    @OFTPType(position = 0, field = Field.DATACMD, format = Format.F, type = Type.X, length = 1)
    private CommandIdentifier datacmd;

    /**
     *  Data Exchange Buffer payload <br>
     *
     * Variable-length buffer containing the data payload.<br>
     */
    @OFTPType(position = 1, field = Field.DATABUF, format = Format.V, type = Type.U, length = OFTPType.NO_LENGTH)
    private byte[] databuf;

    public DATA() {
        this.datacmd = CommandIdentifier.SFPA;
    }

    public DATA(final byte[] byteArray) {
        super.setBytes(byteArray);
    }
}
