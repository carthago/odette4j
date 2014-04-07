package com.de.grossmann.carthago.protocol.odette.data.command;

import com.de.grossmann.carthago.protocol.odette.data.OFTPType;

/**
 * o-------------------------------------------------------------------o
 * |       CD          Change Direction                                |
 * |                                                                   |
 * |       Start File Phase           Speaker ----> Listener           |
 * |       End File Phase             Speaker ----> Listener           |
 * |       End Session Phase        Initiator <---> Responder          |
 * |-------------------------------------------------------------------|
 * | Pos | Field     | Description                           | Format  |
 * |-----+-----------+---------------------------------------+---------|
 * |   0 | CDCMD     | CD Command, 'R'                       | F X(1)  |
 * o-------------------------------------------------------------------o
 */
public class CD extends Command {

    /**
     * Command Code.<br>
     * 'R' CD Command identifier.
     */
    @OFTPType(position = 0, format = OFTPType.Format.F, type = OFTPType.Type.X, length = 1)
    private CommandIdentifier cdcmd;

    public CD() {
        this.cdcmd = CommandIdentifier.CD;

    }

    public CD(final byte[] byteArray) {
        super.setBytes(byteArray);
    }
}
