package com.de.grossmann.carthago.protocol.odette.data.commands;

import com.de.grossmann.carthago.protocol.odette.data.OFTPType;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType.Format;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType.Type;

/**
 * o-------------------------------------------------------------------o
 * |       SSID        Start Session                                   |
 * |                                                                   |
 * |       Start Session Phase     Initiator <---> Responder           |
 * |-------------------------------------------------------------------|
 * | Pos | Field     | Description                           | Format  |
 * |-----+-----------+---------------------------------------+---------|
 * |   0 | SSIDCMD   | SSID Command 'X'                      | F X(1)  |
 * |   1 | SSIDLEV   | Protocol Release Level                | F 9(1)  |
 * |   2 | SSIDCODE  | Initiator's Identification Code       | V X(25) |
 * |  27 | SSIDPSWD  | Initiator's Password                  | V X(8)  |
 * |  35 | SSIDSDEB  | Data Exchange Buffer Size             | V 9(5)  |
 * |  40 | SSIDSR    | Send / Receive Capabilities (S/R/B)   | F X(1)  |
 * |  41 | SSIDCMPR  | Buffer Compression Indicator (Y/N)    | F X(1)  |
 * |  42 | SSIDREST  | Restart Indicator (Y/N)               | F X(1)  |
 * |  43 | SSIDSPEC  | Special Logic Indicator (Y/N)         | F X(1)  |
 * |  44 | SSIDCRED  | Credit                                | V 9(3)  |
 * |  47 | SSIDAUTH  | Secure Authentication (Y/N)           | F X(1)  |
 * |  48 | SSIDRSV1  | Reserved                              | F X(4)  |
 * |  52 | SSIDUSER  | User Data                             | V X(8)  |
 * |  60 | SSIDCR    | Carriage Return                       | F X(1)  |
 * o-------------------------------------------------------------------o
 */
public final class SSID
        extends Command {
    /**
     * Command Code Character.<br>
     * 'X' SSID Command identifier.
     */
    @OFTPType(position = 0, field = OFTPType.Field.SSIDCMD, format = Format.F, type = Type.X, length = 1)
    private CommandIdentifier ssidcmd;
    /**
     * Protocol Release Level.<br>
     * Used to specify the level of the ODETTE-FTP protocol.<br>
     * '1' for Revision 1.2<br>
     * '2' for Revision 1.3<br>
     * '4' for Revision 1.4<br>
     * '5' for Revision 2.0
     */
    @OFTPType(position = 1, field = OFTPType.Field.SSIDLEV, format = Format.F, type = Type._9, length = 1)
    private Long ssidlev;
    /**
     *
     */
    @OFTPType(position = 2, field = OFTPType.Field.SSIDCODE, format = Format.V, type = Type.X, length = 25)
    private String ssidcode;
    /**
     *
     */
    @OFTPType(position = 27, field = OFTPType.Field.SSIDPSWD, format = Format.V, type = Type.X, length = 8)
    private String ssidpswd;
    /**
     *
     */
    @OFTPType(position = 35, field = OFTPType.Field.SSIDSDEB, format = Format.V, type = Type._9, length = 5)
    private Long ssidsdeb;
    /**
     *
     */
    @OFTPType(position = 40, field = OFTPType.Field.SSIDSR, format = Format.F, type = Type.X, length = 1)
    private String ssidsr;
    /**
     *
     */
    @OFTPType(position = 41, field = OFTPType.Field.SSIDCMPR, format = Format.F, type = Type.X, length = 1)
    private String ssidcmpr;
    /**
     *
     */
    @OFTPType(position = 42, field = OFTPType.Field.SSIDREST, format = Format.F, type = Type.X, length = 1)
    private String ssidrest;
    /**
     *
     */
    @OFTPType(position = 43, field = OFTPType.Field.SSIDSPEC, format = Format.F, type = Type.X, length = 1)
    private String ssidspec;
    /**
     *
     */
    @OFTPType(position = 44, field = OFTPType.Field.SSIDCRED, format = Format.V, type = Type._9, length = 3)
    private Long ssidcred;
    /**
     *
     */
    @OFTPType(position = 47, field = OFTPType.Field.SSIDAUTH, format = Format.F, type = Type.X, length = 1)
    private String ssidauth;
    /**
     *
     */
    @OFTPType(position = 48, field = OFTPType.Field.SSIDRSV1, format = Format.F, type = Type.X, length = 4)
    private String ssidrsv1;
    /**
     *
     */
    @OFTPType(position = 52, field = OFTPType.Field.SSIDUSER, format = Format.V, type = Type.X, length = 8)
    private String ssiduser;
    /**
     *
     */
    @OFTPType(position = 60, field = OFTPType.Field.SSIDCR, format = Format.F, type = Type.X, length = 1)
    private String ssidcr;

    public SSID() {
        this.ssidcmd = CommandIdentifier.SSID;
    }

    public SSID(final byte[] byteArray) {
        super.setBytes(byteArray);
    }

    /**
     * @return the ssidlev
     */
    public final Long getSsidlev() {
        return ssidlev;
    }

    /**
     * @param ssidlev the ssidlev to set
     */
    public final void setSsidlev(Long ssidlev) {
        this.ssidlev = ssidlev;
    }

    /**
     * @return the ssidcode
     */
    public final String getSsidcode() {
        return ssidcode;
    }

    /**
     * @param ssidcode the ssidcode to set
     */
    public final void setSsidcode(String ssidcode) {
        this.ssidcode = ssidcode;
    }

    /**
     * @return the ssidpswd
     */
    public final String getSsidpswd() {
        return ssidpswd;
    }

    /**
     * @param ssidpswd the ssidpswd to set
     */
    public final void setSsidpswd(String ssidpswd) {
        this.ssidpswd = ssidpswd;
    }

    /**
     * @return the ssidsdeb
     */
    public final Long getSsidsdeb() {
        return ssidsdeb;
    }

    /**
     * @param ssidsdeb the ssidsdeb to set
     */
    public final void setSsidsdeb(Long ssidsdeb) {
        this.ssidsdeb = ssidsdeb;
    }

    /**
     * @return the ssidsr
     */
    public final String getSsidsr() {
        return ssidsr;
    }

    /**
     * @param ssidsr the ssidsr to set
     */
    public final void setSsidsr(String ssidsr) {
        this.ssidsr = ssidsr;
    }

    /**
     * @return the ssidcmpr
     */
    public final String getSsidcmpr() {
        return ssidcmpr;
    }

    /**
     * @param ssidcmpr the ssidcmpr to set
     */
    public final void setSsidcmpr(String ssidcmpr) {
        this.ssidcmpr = ssidcmpr;
    }

    /**
     * @return the ssidrest
     */
    public final String getSsidrest() {
        return ssidrest;
    }

    /**
     * @param ssidrest the ssidrest to set
     */
    public final void setSsidrest(String ssidrest) {
        this.ssidrest = ssidrest;
    }

    /**
     * @return the ssidspec
     */
    public final String getSsidspec() {
        return ssidspec;
    }

    /**
     * @param ssidspec the ssidspec to set
     */
    public final void setSsidspec(String ssidspec) {
        this.ssidspec = ssidspec;
    }

    /**
     * @return the ssidcred
     */
    public final Long getSsidcred() {
        return ssidcred;
    }

    /**
     * @param ssidcred the ssidcred to set
     */
    public final void setSsidcred(Long ssidcred) {
        this.ssidcred = ssidcred;
    }

    /**
     * @return the ssidauth
     */
    public final String getSsidauth() {
        return ssidauth;
    }

    /**
     * @param ssidauth the ssidauth to set
     */
    public final void setSsidauth(String ssidauth) {
        this.ssidauth = ssidauth;
    }

    /**
     * @return the ssidrsv1
     */
    public final String getSsidrsv1() {
        return ssidrsv1;
    }

    /**
     * @param ssidrsv1 the ssidrsv1 to set
     */
    public final void setSsidrsv1(String ssidrsv1) {
        this.ssidrsv1 = ssidrsv1;
    }

    /**
     * @return the ssiduser
     */
    public final String getSsiduser() {
        return ssiduser;
    }

    /**
     * @param ssiduser the ssiduser to set
     */
    public final void setSsiduser(String ssiduser) {
        this.ssiduser = ssiduser;
    }

    /**
     * @return the ssidcr
     */
    public final String getSsidcr() {
        return ssidcr;
    }

    /**
     * @param ssidcr the ssidcr to set
     */
    public final void setSsidcr(String ssidcr) {
        this.ssidcr = ssidcr;
    }


}
