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
    int position();

    Field field();

    Format format();

    Type type();

    int length();

    enum Field {
        SSRMCMD,  SSRMMSG,  SSRMCR,

        SSIDCMD,   SSIDLEV,  SSIDCODE,
        SSIDPSWD,  SSIDSDEB, SSIDSR,
        SSIDCMPR,  SSIDREST, SSIDSPEC,
        SSIDCRED,  SSIDAUTH, SSIDRSV1,
        SSIDUSER,  SSIDCR,

        ESIDCMD,   ESIDREAS, ESIDREASL,
        ESIDREAST, ESIDCR,

        CDCMD;

    }

    enum Format {
        F,
        V;
    }

    enum Type {
        X("X"),
        _9("9"),
        U("U"),
        T("T");

        private final String value;

        private Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }
}
