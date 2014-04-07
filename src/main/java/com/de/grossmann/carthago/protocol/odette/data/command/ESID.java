package com.de.grossmann.carthago.protocol.odette.data.command;

import com.de.grossmann.carthago.protocol.odette.data.OFTPType;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType.Format;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType.Type;

/**
 * o-------------------------------------------------------------------o
 * |       ESID        End Session                                     |
 * |                                                                   |
 * |       End Session Phase          Speaker ----> Listener           |
 * |-------------------------------------------------------------------|
 * | Pos | Field     | Description                           | Format  |
 * |-----+-----------+---------------------------------------+---------|
 * |   0 | ESIDCMD   | ESID Command, 'F'                     | F X(1)  |
 * |   1 | ESIDREAS  | Reason Code                           | F 9(2)  |
 * |   3 | ESIDREASL | Reason Text Length                    | V 9(3)  |
 * |   6 | ESIDREAST | Reason Text                           | V T(n)  |
 * |     | ESIDCR    | Carriage Return                       | F X(1)  |
 * o-------------------------------------------------------------------o
 */
public final class ESID
        extends Command {

    /**
     * ESID Command, 'F'
     */
    @OFTPType(position = 0, format = Format.F, type = Type.X, length = 1)
    private CommandIdentifier esidcmd;
    /**
     *  Reason Code
     */
    @OFTPType(position = 1, format = Format.F, type = Type._9, length = 2)
    private Long esidreas;
    /**
     * Reason Text Length
     */
    @OFTPType(position = 3, format = Format.V, type = Type._9, length = 3)
    private String esidreasl;
    /**
     * Reason Text
     */
    @OFTPType(position = 6, format = Format.V, type = Type.T, length = 9999)
    private String esidreast;
    /**
     * Carriage Return
     */
    @OFTPType(position = 9999, format = Format.F, type = Type.X, length = 1)
    private String esidcr;

    public ESID() {
        this.esidcmd = CommandIdentifier.ESID;
    }

    public ESID(final byte[] byteArray) {
        super.setBytes(byteArray);
    }

}
