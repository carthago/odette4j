/**
 *
 */
package com.de.grossmann.carthago.protocol.odette.data.commands;

import com.de.grossmann.carthago.common.ByteUtils;
import com.de.grossmann.carthago.protocol.odette.data.OFTPType;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Micha
 */
public abstract class Command
{
    private static final String CODEPAGE = "ASCII";

    public final byte[] getBytes()
    {
        Field[] fields = this.getClass().getDeclaredFields();
        Arrays.sort(fields, new OFTPFieldComparator());
        ByteBuffer byteBuffer = ByteBuffer.allocate(0);

        for (int ii = 0; ii < fields.length; ii++)
        {
            Field field = fields[ii];
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (int jj = 0; jj < annotations.length; jj++)
            {
                Annotation annotation = annotations[jj];
                if (annotation.annotationType().isAssignableFrom(OFTPType.class))
                {
                    OFTPType type = (OFTPType) annotation;
                    int length = getLength(fields, ii, type);

                    ByteBuffer newByteBuffer = ByteBuffer.allocate(length + byteBuffer.capacity());
                    newByteBuffer.put(byteBuffer.array());
                    newByteBuffer.put(this.getBytesFromField(field, type, length));
                    byteBuffer = newByteBuffer;
                }
            }
        }
        return byteBuffer.array();
    }

    protected final void setBytes(byte[] byteArray)
    {

        Field[] fields = this.getClass().getDeclaredFields();
        Arrays.sort(fields, new OFTPFieldComparator());


        int lastPosition = 0;

        for (int ii = 0; ii < fields.length; ii++)
        {
            int currentLength;
            int currentPosition;

            Field currentField = fields[ii];

            OFTPType oftpType = currentField.getAnnotation(OFTPType.class);

            // check for fields with a referenced length field
            switch (oftpType.length()) {
                case OFTPType.NO_LENGTH:
                    currentLength = getLength(fields, ii, oftpType);
                    break;
                case OFTPType.IMPLICIT_LENGTH:
                    currentLength = byteArray.length - 1;
                    break;
                default:
                    currentLength = currentField.getAnnotation(OFTPType.class).length();
                    break;
            }

            // calculate position
            currentPosition = getPosition(fields, ii, lastPosition, oftpType);

            this.setFieldByBytes(currentField, currentPosition, currentLength, byteArray, oftpType);

            lastPosition = currentPosition + currentLength;
        }
    }

    private int getLength(final Field[] fields, final int currentField, final OFTPType oftpType)
    {
        int length = 0;

        if (oftpType.length() == OFTPType.NO_LENGTH)
        {
            for (int jj = currentField - 1; jj >= 0; jj--)
            {
                Field field = fields[jj];

                if (field.getAnnotation(OFTPType.class).field() == oftpType.lengthField())
                {
                    boolean accessible = fields[jj].isAccessible();
                    field.setAccessible(true);

                    try
                    {
                        if (field.getType().isAssignableFrom(Short.class)) {
                            length = ((Short)field.get(this)).intValue();
                        } else if (field.getType().isAssignableFrom(Integer.class)) {
                            length = (Integer)(field.get(this));
                        } else if (field.getType().isAssignableFrom(Long.class)) {
                            length = ((Long)field.get(this)).intValue();
                        } else if (field.getType().isAssignableFrom(BigInteger.class)) {
                            length = ((BigInteger)field.get(this)).intValue();
                        } else if (field.getType().isAssignableFrom(String.class)){
                            length = new Integer((String)field.get(this));
                        } else {
                            throw new IllegalArgumentException("Invalid type for length field. [" + field.getType().getSimpleName() + "]");
                        }
                    }
                    catch (IllegalAccessException e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        field.setAccessible(accessible);
                        break;
                    }
                }
            }
        }
        else
        {
            length = fields[currentField].getAnnotation(OFTPType.class).length();
        }
        return length;
    }

    private int getPosition(final Field[] fields, final int currentField, final int lastPosition, final OFTPType oftpType)
    {
        int newPosition;

        if (oftpType.position() == OFTPType.NO_POSITION)
        {
            newPosition = lastPosition;
        }
        else
        {
            newPosition = (fields[currentField].getAnnotation(OFTPType.class).position());
        }
        return newPosition;
    }


    private final void setFieldByBytes(final Field currentField, final int currentPosition, final int currentLength, final byte[] byteArray, final OFTPType oftpType)
    {
        currentField.setAccessible(true);
        try
        {
            if ((currentPosition < byteArray.length) && ((currentPosition + currentLength) <= byteArray.length))
            {
                if (currentField.getType().isAssignableFrom(String.class))
                {
                    this.setStringByBytes(currentField, byteArray, currentPosition, currentLength);
                }
                else if (currentField.getType().isAssignableFrom(Integer.class))
                {
                    this.setIntegerByBytes(currentField, byteArray, currentPosition, currentLength);
                }
                else if (currentField.getType().isAssignableFrom(Long.class))
                {
                    this.setLongByBytes(currentField, byteArray, currentPosition, currentLength);
                }
                else if (currentField.getType().isAssignableFrom(BigInteger.class))
                {
                    this.setBigIntegerByBytes(currentField, byteArray, currentPosition, currentLength);
                }
                else if (currentField.getType().isAssignableFrom(byte[].class))
                {
                    this.setByteArrayByBytes(currentField, byteArray, currentPosition, currentLength);
                }
                else if (currentField.getType().isAssignableFrom(CommandIdentifier.class))
                {
                    this.setCommandIdentifierByBytes(currentField, byteArray, currentPosition, currentLength);
                }
            }
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    private final void setStringByBytes(Field field, final byte[] byteArray, final int currentPosition, final int currentLength)
            throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException
    {
        String newValue;

        newValue = new String(byteArray, currentPosition, currentLength, Command.CODEPAGE);
        field.set(this, newValue);
    }

    private final void setIntegerByBytes(Field field, final byte[] byteArray, final int currentPosition, final int currentLength)
            throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException
    {
        String newValue;
        newValue = new String(byteArray, currentPosition, currentLength, Command.CODEPAGE);
        Integer newInt = Integer.parseInt(newValue);
        field.set(this, newInt);
    }

    private final void setLongByBytes(Field field, final byte[] byteArray, final int currentPosition, final int currentLength)
            throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException
    {
        String newValue;
        newValue = new String(byteArray, currentPosition, currentLength, Command.CODEPAGE);
        Long newLong = Long.parseLong(newValue);
        field.set(this, newLong);
    }


    private final void setBigIntegerByBytes(Field field, final byte[] byteArray, final int currentPosition, final int currentLength)
            throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException
    {
        String newValue;
        newValue = new String(byteArray, currentPosition, currentLength, Command.CODEPAGE);
        BigInteger newBigInteger = BigInteger.valueOf(Long.parseLong(newValue));
        field.set(this, newBigInteger);
    }

    private final void setByteArrayByBytes(Field field, final byte[] byteArray, final int currentPosition, final int currentLength)
        throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException
    {
        field.set(this, Arrays.copyOfRange(byteArray, currentPosition, currentPosition + currentLength));
    }

    private final void setCommandIdentifierByBytes(Field field, final byte[] byteArray, final int currentPosition, final int currentLength)
            throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException
    {
        // TODO besser machen :)
        char newValue;
        newValue = new String(byteArray, currentPosition, currentLength, Command.CODEPAGE).charAt(0);
        CommandIdentifier newCommandIdentifier = CommandIdentifier.valueOf(newValue);
        field.set(this, newCommandIdentifier);
    }

    private final byte[] getBytesFromField(final Field field, final OFTPType type, final int length)
    {
        ByteBuffer byteBuffer = ByteBuffer.allocate(length);
        field.setAccessible(true);
        try
        {
            if (field != null)
            {
                Object fieldObject = field.get(this);
                if (fieldObject != null)
                {
                    if (field.getType().isAssignableFrom(CommandIdentifier.class))
                    {
                        byteBuffer.put((byte) ((CommandIdentifier) fieldObject).getIdentifier());
                    }
                    else if (field.getType().isAssignableFrom(String.class))
                    {
                        byteBuffer.put(this.getBytesFromString((String) fieldObject, type, length), 0, length);
                    }
                    else if (field.getType().isAssignableFrom(Integer.class))
                    {
                        byteBuffer.put(this.getBytesFromInteger((Integer) fieldObject, type, length), 0, length);
                    }
                    else if (field.getType().isAssignableFrom(int.class))
                    {
                        byteBuffer.put(this.getBytesFromInteger((Integer) fieldObject, type, length), 0, length);
                    }
                    else if (field.getType().isAssignableFrom(Long.class))
                    {
                        byteBuffer.put(this.getBytesFromLong((Long) fieldObject, type, length), 0, length);
                    }
                    else if (field.getType().isAssignableFrom(long.class))
                    {
                        byteBuffer.put(this.getBytesFromLong((Long) fieldObject, type, length), 0, length);
                    }
                    else if (field.getType().isAssignableFrom(BigInteger.class))
                    {
                        byteBuffer.put(this.getBytesFromBigInteger((BigInteger) fieldObject, type, length), 0, length);
                    }
                }
            }
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return byteBuffer.array();
    }

    private final byte[] getBytesFromString(String field, final OFTPType type, final int length)
            throws UnsupportedEncodingException
    {
        String codePageToUse = CODEPAGE;
        if (type.type() == OFTPType.Type.T)
        {
            codePageToUse = "UTF-8";
        }

        byte[] byteArray;

        String format = "%-" + String.format("%d", length) + "S";
        byteArray = String.format(format, field).getBytes(codePageToUse);
        return byteArray;
    }

    private final byte[] getBytesFromLong(final Long field, final OFTPType type, final int length)
            throws UnsupportedEncodingException
    {
        String format = "%0" + String.format("%d", length) + "d";
        return this.getBytesFromString(String.format(format, field), type, length);
    }

    private final byte[] getBytesFromInteger(final Integer field, final OFTPType type, final int length)
            throws UnsupportedEncodingException
    {
        String format = "%0" + String.format("%d", length) + "d";
        return this.getBytesFromString(String.format(format, field), type, length);
    }

    private final byte[] getBytesFromBigInteger(final BigInteger field, final OFTPType type, final int length)
            throws UnsupportedEncodingException
    {
        String format = "%0" + String.format("%d", length) + "d";
        return this.getBytesFromString(String.format(format, field), type, length );
    }

    public String toString()
    {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(this.getClass().getName() + "\n");
        Field[] fields = this.getClass().getDeclaredFields();
        Arrays.sort(fields, new OFTPFieldComparator());

        for (int ii = 0; ii < fields.length; ii++)
        {
            Field field = fields[ii];
            Annotation[] annotations = field.getDeclaredAnnotations();
            field.setAccessible(true);

            for (int jj = 0; jj < annotations.length; jj++)
            {
                Annotation annotation = annotations[jj];
                if (annotation.annotationType().isAssignableFrom(OFTPType.class))
                {
                    OFTPType oftpType = (OFTPType) annotation;

                    // if position is set to Integer.MAX_VALUE
                    // the real position depends on the length
                    // of an other field
                    if (oftpType.position() == OFTPType.NO_POSITION)
                    {
                        stringBuffer.append("    | ");
                    }
                    else
                    {
                        stringBuffer.append(String.format("%3d", oftpType.position()) + " | ");
                    }
                    stringBuffer.append(String.format("%-12S", oftpType.field()) + " | ");
                    stringBuffer.append(oftpType.format() + " ");
                    stringBuffer.append(oftpType.type() + "(");
                    stringBuffer.append(String.format("%3d", getLength(fields,ii,oftpType)));
                    stringBuffer.append(") | ");
                    try
                    {
                        if (oftpType.type() == OFTPType.Type.U)
                        {
                            stringBuffer.append(ByteUtils.bytesToHex((byte[])field.get(this)));
                        }
                        else
                        {
                            stringBuffer.append(field.get(this));
                        }
                    }
                    catch (IllegalArgumentException | IllegalAccessException e)
                    {
                        e.printStackTrace();
                    }

                    stringBuffer.append("\n");
                }
            }
        }
        return stringBuffer.toString();
    }

    final class OFTPFieldComparator
            implements Comparator<Field>
    {
        @Override
        public int compare(Field arg0, Field arg1)
        {
            int rc = 0;
            OFTPType type0 = null;
            OFTPType type1 = null;

            Annotation[] annotations0 = arg0.getDeclaredAnnotations();
            Annotation[] annotations1 = arg1.getDeclaredAnnotations();

            if (annotations0 != null)
            {
                for (int jj = 0; jj < annotations0.length; jj++)
                {
                    Annotation annotation = annotations0[jj];
                    if (annotation.annotationType().isAssignableFrom(OFTPType.class))
                    {
                        type0 = (OFTPType) annotation;
                    }
                }
            }

            if (annotations1 != null)
            {
                for (int jj = 0; jj < annotations1.length; jj++)
                {
                    Annotation annotation = annotations1[jj];
                    if (annotation.annotationType().isAssignableFrom(OFTPType.class))
                    {
                        type1 = (OFTPType) annotation;
                    }
                }
            }

            if ((type0 != null) && (type1 != null))
            {
                if (type0.field().getPosition() < type1.field().getPosition())
                {
                    rc = -1;
                }
                else if (type0.field().getPosition() > type1.field().getPosition())
                {
                    rc = 1;
                }
                else
                {
                    rc = 0;
                }
            }
            else
            {
                rc = 0;
            }
            return rc;
        }
    }
}

