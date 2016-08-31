package com.de.grossmann.carthago.protocol.odette.data.commands;

import com.de.grossmann.carthago.protocol.odette.data.OFTPType;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType.Field;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType.Format;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType.Type;

/**
 * o-------------------------------------------------------------------o
 * |       SFID        Start File                                      |
 * |                                                                   |
 * |       Start File Phase           Speaker ----> Listener           |
 * |-------------------------------------------------------------------|
 * | Pos | Field     | Description                           | Format  |
 * |-----+-----------+---------------------------------------+---------|
 * |   0 | SFIDCMD   | SFID Command, 'H'                     | F X(1)  |
 * |   1 | SFIDDSN   | Virtual File Dataset Name             | V X(26) |
 * |  27 | SFIDRSV1  | Reserved                              | F X(3)  |
 * |  30 | SFIDDATE  | Virtual File Date stamp, (CCYYMMDD)   | V 9(8)  |
 * |  38 | SFIDTIME  | Virtual File Time stamp, (HHMMSScccc) | V 9(10) |
 * |  48 | SFIDUSER  | User Data                             | V X(8)  |
 * |  56 | SFIDDEST  | Destination                           | V X(25) |
 * |  81 | SFIDORIG  | Originator                            | V X(25) |
 * | 106 | SFIDFMT   | File Format (F/V/U/T)                 | F X(1)  |
 * | 107 | SFIDLRECL | Maximum Record Size                   | V 9(5)  |
 * | 112 | SFIDFSIZ  | File Size, 1K blocks                  | V 9(13) |
 * | 125 | SFIDOSIZ  | Original File Size, 1K blocks         | V 9(13) |
 * | 138 | SFIDREST  | Restart Position                      | V 9(17) |
 * | 155 | SFIDSEC   | Security Level                        | F 9(2)  |
 * | 157 | SFIDCIPH  | Cipher suite selection                | F 9(2)  |
 * | 159 | SFIDCOMP  | File compression algorithm            | F 9(1)  |
 * | 160 | SFIDENV   | File enveloping format                | F 9(1)  |
 * | 161 | SFIDSIGN  | Signed EERP request                   | F X(1)  |
 * | 162 | SFIDDESCL | Virtual File Description length       | V 9(3)  |
 * | 165 | SFIDDESC  | Virtual File Description              | V T(n)  |
 * o-------------------------------------------------------------------o
 */
public final class SFID
        extends Command {

    /**
     * Command Code Character.<br>
     * 'H' SFID Command identifier.
     */
    @OFTPType(position = 0, field = Field.SFIDCMD, format = Format.F, type = Type.X, length = 1)
    private CommandIdentifier sfidcmd;

    @OFTPType(position = 1,field = Field.SFIDDSN, format = Format.V, type = Type.X, length = 26)
    private String sfiddsn;


    public SFID() {
        this.sfidcmd = CommandIdentifier.SFID;
    }

    public SFID(final byte[] byteArray) {
        super.setBytes(byteArray);
    }

}
