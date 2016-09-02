package com.de.grossmann.carthago.protocol.odette.data.commands;

import com.de.grossmann.carthago.protocol.odette.data.OFTPType;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType.Field;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType.Format;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType.Type;

/**
 * o-------------------------------------------------------------------o
 * |       CDT         Set Credit                                      |
 * |                                                                   |
 * |       Data Transfer Phase        Speaker <---- Listener           |
 * |-------------------------------------------------------------------|
 * | Pos | Field     | Description                           | Format  |
 * |-----+-----------+---------------------------------------+---------|
 * |   0 | CDTCMD    | CDT Command, 'C'                      | F X(1)  |
 * |   1 | CDTRSV1   | Reserved                              | F X(2)  |
 * o-------------------------------------------------------------------o
 */
public final class CDT
        extends Command {

    /**
     * Command Code Character.<br>
     * 'C' CDT Command identifier.
     */
    @OFTPType(position = 0, field = Field.CDTCMD, format = Format.F, type = Type.X, length = 1)
    private CommandIdentifier cdtcmd;

    /**
     *  Reserved <br>
     */
    @OFTPType(position = 1, field = Field.CDTRSV1, format = Format.F, type = Type.X, length = 2)
    private String cdtrsv1;

    public CDT() {
        this.cdtcmd = CommandIdentifier.CDT;
    }

    public CDT(final byte[] byteArray) {
        super.setBytes(byteArray);
    }

    public String getCdtrsv1()
    {
        return cdtrsv1;
    }

    public void setCdtrsv1(String cdtrsv1)
    {
        this.cdtrsv1 = cdtrsv1;
    }
}
