/**
 *
 */
package com.de.grossmann.carthago.protocol.odette.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Micha
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OFTPType {

    int NO_POSITION     = -1;
    int NO_LENGTH       = -2;
    int IMPLICIT_LENGTH = -3;

    int    position() default NO_POSITION;
    int    length()   default NO_LENGTH;

    Field  field();
    Format format();
    Type   type();
    Field  lengthField() default Field.NONE;

    enum Field {
        NONE(-1),

        // SSRM
        SSRMCMD(0),    SSRMMSG(1),   SSRMCR(2),

        // SSID
        SSIDCMD(0),    SSIDLEV(1),   SSIDCODE(2),
        SSIDPSWD(3),   SSIDSDEB(4),  SSIDSR(5),
        SSIDCMPR(6),   SSIDREST(7),  SSIDSPEC(8),
        SSIDCRED(9),   SSIDAUTH(10), SSIDRSV1(11),
        SSIDUSER(12),  SSIDCR(13),

        // ESID
        ESIDCMD(0),    ESIDREAS(1),  ESIDREASL(2),
        ESIDREAST(3),  ESIDCR(4),

        // SFID
        SFIDCMD(0),    SFIDDSN(1),   SFIDRSV1(2),
        SFIDDATE(3),   SFIDTIME(4),  SFIDUSER(5),
        SFIDDEST(6),   SFIDORIG(7),  SFIDFMT(8),
        SFIDLRECL(9),  SFIDFSIZ(10), SFIDOSIZ(11),
        SFIDREST(12),  SFIDSEC(13),  SFIDCIPH(14),
        SFIDCOMP(15),  SFIDENV(16),  SFIDSIGN(17),
        SFIDDESCL(18), SFIDDESC(19),

        // SFPA
        SFPACMD(0),    SFPAACNT(1),

        //DATA
        DATACMD(0),    DATABUF(1),

        // CDT
        CDTCMD(0),     CDTRSV1(1),

        // EFID
        EFIDCMD(0),    EFIDRCNT(1),   EFIDUCNT(2),

        // EFPA
        EFPACMD(0), EFPACD(1),

        // CD
        CDCMD(0);

        private final int position;

        Field(final int position)
        {
            this.position = position;
        }

        public int getPosition()
        {
            return this.position;
        }
    }

    enum Format
    {
        F,
        V
    }

    enum Type
    {
        X("X"),
        _9("9"),
        U("U"),
        T("T");

        private final String value;

        Type(final String value)
        {
            this.value = value;
        }

        @Override
        public String toString()
        {
            return this.value;
        }
    }
}
