package com.de.grossmann.carthago.protocol.odette.data.command;

import com.de.grossmann.carthago.protocol.odette.data.OFTPType;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType.Format;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType.Type;

/**
 * o-------------------------------------------------------------------o
 * |       SSRM        Start Session Ready Message                     |
 * |                                                                   |
 * |       Start Session Phase     Initiator <---- Responder           |
 * |-------------------------------------------------------------------|
 * | Pos | Field     | Description                           | Format  |
 * |-----+-----------+---------------------------------------+---------|
 * |   0 | SSRMCMD   | SSRM Command, 'I'                     | F X(1)  |
 * |   1 | SSRMMSG   | Ready Message, 'ODETTE FTP READY '    | F X(17) |
 * |  18 | SSRMCR    | Carriage Return                       | F X(1)  |
 * o-------------------------------------------------------------------o
 */
public final class SSRM
        extends Command {
    /**
     * Command Code.<br>
     * 'I' SSRM Command identifier.
     */
    @OFTPType(position = 0, format = Format.F, type = Type.X, length = 1)
    private CommandIdentifier ssrmcmd;

    /**
     * Ready Message.<br>
     * 'ODETTE FTP READY '.
     */
    @OFTPType(position = 1, format = Format.F, type = Type.X, length = 17)
    private String ssrmmsg;

    /**
     * Carriage Return.<br>
     * Character with hex value '0D' or '8D'.
     */
    @OFTPType(position = 18, format = Format.F, type = Type.X, length = 1)
    private String ssrmcr;

    public SSRM() {
        this.ssrmcmd = CommandIdentifier.SSRM;
        this.ssrmmsg = "ODETTE FTP READY";
        this.ssrmcr = new String(new char[]{0x0D});
    }

    public SSRM(final byte[] byteArray) {
        super.setBytes(byteArray);
    }
}
