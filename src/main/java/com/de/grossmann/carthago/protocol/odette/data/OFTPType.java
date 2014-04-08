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

    public static int NO_POSITION = -1;
    public static int NO_LENGTH   = -1;

    int    position() default NO_POSITION;
    int    length()   default NO_LENGTH;

    Field  field();
    Format format();
    Type   type();
    Field  lengthField() default Field.NONE;

    enum Field {
        NONE(-1),

        SSRMCMD(0),    SSRMMSG(1),   SSRMCR(2),

        SSIDCMD(0),    SSIDLEV(1),   SSIDCODE(2),
        SSIDPSWD(3),   SSIDSDEB(4),  SSIDSR(5),
        SSIDCMPR(6),   SSIDREST(7),  SSIDSPEC(8),
        SSIDCRED(9),   SSIDAUTH(10), SSIDRSV1(11),
        SSIDUSER(12),  SSIDCR(13),

        ESIDCMD(0),    ESIDREAS(1),  ESIDREASL(2),
        ESIDREAST(3),  ESIDCR(4),

        CDCMD(0);

        private final int position;

        private Field(final int position)
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
        V;
    }

    enum Type
    {
        X("X"),
        _9("9"),
        U("U"),
        T("T");

        private final String value;

        private Type(final String value)
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
