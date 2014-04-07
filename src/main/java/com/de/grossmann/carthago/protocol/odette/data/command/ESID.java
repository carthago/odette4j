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

    @OFTPType(position = 0, field = OFTPType.Field.ESIDCMD, format = Format.F, type = Type.X, length = 1)
    private CommandIdentifier esidcmd;

    @OFTPType(position = 1, field = OFTPType.Field.ESIDREAS, format = Format.F, type = Type._9, length = 2)
    private Long esidreas;

    @OFTPType(position = 3, field = OFTPType.Field.ESIDREASL, format = Format.V, type = Type._9, length = 3)
    private String esidreasl;

    @OFTPType(position = 6, field = OFTPType.Field.ESIDREAST, format = Format.V, type = Type.T, length = OFTPType.Field.ESIDREASL)
    private String esidreast;

    @OFTPType(position = OFTPType.Field.ESIDREAST, field = OFTPType.Field.ESIDCR, format = Format.F, type = Type.X, length = 1)
    private String esidcr;

    public ESID() {
        this.esidcmd = CommandIdentifier.ESID;
    }

    public ESID(final byte[] byteArray) {
        super.setBytes(byteArray);
    }

}
