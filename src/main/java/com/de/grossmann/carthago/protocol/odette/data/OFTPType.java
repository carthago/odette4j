/**
 *
 */
package com.de.grossmann.carthago.protocol.odette.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** @author Micha */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OFTPType {
    int position();

    Format format();

    Type type();

    int length();

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
