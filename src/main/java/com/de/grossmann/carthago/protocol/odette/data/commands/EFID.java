package com.de.grossmann.carthago.protocol.odette.data.commands;

import com.de.grossmann.carthago.protocol.odette.data.OFTPType;

/**
 * o-------------------------------------------------------------------o
 * |       EFID        End File                                        |
 * |                                                                   |
 * |       End File Phase             Speaker ----> Listener           |
 * |-------------------------------------------------------------------|
 * | Pos | Field     | Description                           | Format  |
 * |-----+-----------+---------------------------------------+---------|
 * |   0 | EFIDCMD   | EFID Command, 'T'                     | F X(1)  |
 * |   1 | EFIDRCNT  | Record Count                          | V 9(17) |
 * |  18 | EFIDUCNT  | Unit Count                            | V 9(17) |
 * o-------------------------------------------------------------------o
 */
public final class EFID
        extends Command {

    /**
     * Command Code Character.<br>
     * 'T' EFID Command identifier.
     */
    @OFTPType(position = 0, field = OFTPType.Field.EFIDCMD, format = OFTPType.Format.F, type = OFTPType.Type.X, length = 1)
    private CommandIdentifier efidcmd;

    /**
     *  Record Count <br>
     */
    @OFTPType(position = 1, field = OFTPType.Field.EFIDRCNT, format = OFTPType.Format.V, type = OFTPType.Type._9, length = 17)
    private Long efidrcnt;

    /**
     *  Unit Count <br>
     */
    @OFTPType(position = 18, field = OFTPType.Field.EFIDUCNT, format = OFTPType.Format.V, type = OFTPType.Type._9, length = 17)
    private String efiducnt;

    public EFID() {
        this.efidcmd = CommandIdentifier.EFID;
    }

    public EFID(final byte[] byteArray) {
        super.setBytes(byteArray);
    }
}
