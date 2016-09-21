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

    @OFTPType(position = 1, field = Field.SFIDDSN, format = Format.V, type = Type.X, length = 26)
    private String sfiddsn;

    @OFTPType(position = 27, field = Field.SFIDRSV1, format = Format.F, type = Type.X, length = 3)
    private String sfidrsv1;

    @OFTPType(position = 30, field = Field.SFIDDATE, format = Format.V, type = Type._9, length = 8)
    private Long sfiddate;

    @OFTPType(position = 38, field = Field.SFIDTIME, format = Format.V, type = Type._9, length = 10)
    private Long sfidtime;

    @OFTPType(position = 48, field = Field.SFIDUSER, format = Format.V, type = Type.X, length = 8)
    private String sfiduser;

    @OFTPType(position = 56, field = Field.SFIDDEST, format = Format.V, type = Type.X, length = 25)
    private String sfiddest;

    @OFTPType(position = 81, field = Field.SFIDORIG, format = Format.V, type = Type.X, length = 25)
    private String sfidorig;

    @OFTPType(position = 106, field = Field.SFIDFMT, format = Format.F, type = Type.X, length = 1)
    private String sfidfmt;

    @OFTPType(position = 107, field = Field.SFIDLRECL, format = Format.V, type = Type._9, length = 5)
    private Long sfidlrecl;

    @OFTPType(position = 112, field = Field.SFIDFSIZ, format = Format.V, type = Type._9, length = 13)
    private Long sfidfsiz;

    @OFTPType(position = 125, field = Field.SFIDOSIZ, format = Format.V, type = Type._9, length = 13)
    private Long sfidosiz;

    @OFTPType(position = 138, field = Field.SFIDREST, format = Format.V, type = Type._9, length = 17)
    private Long sfidrest;

    @OFTPType(position = 155, field = Field.SFIDSEC , format = Format.F, type = Type._9, length = 2)
    private Long sfidsec;

    @OFTPType(position = 157, field = Field.SFIDCIPH, format = Format.F, type = Type._9, length = 2)
    private Long sfidciph;

    @OFTPType(position = 159, field = Field.SFIDCOMP, format = Format.F, type = Type._9, length = 1)
    private Long sfidcomp;

    @OFTPType(position = 160, field = Field.SFIDENV, format = Format.F, type = Type._9, length = 1)
    private Long sfidenv;

    @OFTPType(position = 161, field = Field.SFIDSIGN, format = Format.F, type = Type.X, length = 1)
    private String sfidsign;

    @OFTPType(position = 162, field = Field.SFIDDESCL, format = Format.V, type = Type._9, length = 3)
    private Long sfiddescl;

    @OFTPType(position = 165, field = Field.SFIDDESC, format = Format.V, type = Type.T, lengthField = Field.SFIDDESCL)
    private String sfiddesc;


    public SFID() {
        this.sfidcmd = CommandIdentifier.SFID;
    }

    public SFID(final byte[] byteArray) {
        super.setBytes(byteArray);
    }

    public String getSfiddsn()
    {
        return sfiddsn;
    }

    public void setSfiddsn(String sfiddsn)
    {
        this.sfiddsn = sfiddsn;
    }

    public String getSfidrsv1()
    {
        return sfidrsv1;
    }

    public void setSfidrsv1(String sfidrsv1)
    {
        this.sfidrsv1 = sfidrsv1;
    }

    public Long getSfiddate()
    {
        return sfiddate;
    }

    public void setSfiddate(Long sfiddate)
    {
        this.sfiddate = sfiddate;
    }

    public Long getSfidtime()
    {
        return sfidtime;
    }

    public void setSfidtime(Long sfidtime)
    {
        this.sfidtime = sfidtime;
    }

    public String getSfiduser()
    {
        return sfiduser;
    }

    public void setSfiduser(String sfiduser)
    {
        this.sfiduser = sfiduser;
    }

    public String getSfiddest()
    {
        return sfiddest;
    }

    public void setSfiddest(String sfiddest)
    {
        this.sfiddest = sfiddest;
    }

    public String getSfidorig()
    {
        return sfidorig;
    }

    public void setSfidorig(String sfidorig)
    {
        this.sfidorig = sfidorig;
    }

    public String getSfidfmt()
    {
        return sfidfmt;
    }

    public void setSfidfmt(String sfidfmt)
    {
        this.sfidfmt = sfidfmt;
    }

    public Long getSfidlrecl()
    {
        return sfidlrecl;
    }

    public void setSfidlrecl(Long sfidlrecl)
    {
        this.sfidlrecl = sfidlrecl;
    }

    public Long getSfidfsiz()
    {
        return sfidfsiz;
    }

    public void setSfidfsiz(Long sfidfsiz)
    {
        this.sfidfsiz = sfidfsiz;
    }

    public Long getSfidosiz()
    {
        return sfidosiz;
    }

    public void setSfidosiz(Long sfidosiz)
    {
        this.sfidosiz = sfidosiz;
    }

    public Long getSfidrest()
    {
        return sfidrest;
    }

    public void setSfidrest(Long sfidrest)
    {
        this.sfidrest = sfidrest;
    }

    public Long getSfidsec()
    {
        return sfidsec;
    }

    public void setSfidsec(Long sfidsec)
    {
        this.sfidsec = sfidsec;
    }

    public Long getSfidciph()
    {
        return sfidciph;
    }

    public void setSfidciph(Long sfidciph)
    {
        this.sfidciph = sfidciph;
    }

    public Long getSfidcomp()
    {
        return sfidcomp;
    }

    public void setSfidcomp(Long sfidcomp)
    {
        this.sfidcomp = sfidcomp;
    }

    public Long getSfidenv()
    {
        return sfidenv;
    }

    public void setSfidenv(Long sfidenv)
    {
        this.sfidenv = sfidenv;
    }

    public String getSfidsign()
    {
        return sfidsign;
    }

    public void setSfidsign(String sfidsign)
    {
        this.sfidsign = sfidsign;
    }

    public Long getSfiddescl()
    {
        return sfiddescl;
    }

    public void setSfiddescl(Long sfiddescl)
    {
        this.sfiddescl = sfiddescl;
    }

    public String getSfiddesc()
    {
        return sfiddesc;
    }

    public void setSfiddesc(String sfiddesc)
    {
        this.sfiddesc = sfiddesc;
    }
}
