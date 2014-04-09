/**
 *
 */
package com.de.grossmann.carthago.protocol.odette.data.commands;

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
                    ByteBuffer newByteBuffer = ByteBuffer.allocate(type.length() + byteBuffer.capacity());
                    newByteBuffer.put(byteBuffer.array());
                    newByteBuffer.put(this.getBytesFromField(field, type));
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
            currentLength = getLength(fields, ii, oftpType);

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
                            length = new Integer((String)field.get(this));
                        } else if (field.getType().isAssignableFrom(Integer.class)) {
                            length = new Integer((String)field.get(this));
                        } else if (field.getType().isAssignableFrom(Long.class)) {
                            length = new Integer((String)field.get(this));
                        } else if (field.getType().isAssignableFrom(BigInteger.class)) {
                            length = new Integer(((BigInteger)field.get(this)).intValue());
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

    private final void setCommandIdentifierByBytes(Field field, final byte[] byteArray, final int currentPosition, final int currentLength)
            throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException
    {
        // TODO besser machen :)
        char newValue;
        newValue = new String(byteArray, currentPosition, currentLength, Command.CODEPAGE).charAt(0);
        CommandIdentifier newCommandIdentifier = CommandIdentifier.valueOf(newValue);
        field.set(this, newCommandIdentifier);
    }

    private final byte[] getBytesFromField(final Field field, final OFTPType type)
    {
        ByteBuffer byteBuffer = ByteBuffer.allocate(type.length());
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
                        byteBuffer.put(this.getBytesFromString((String) fieldObject, type), 0, type.length());
                    }
                    else if (field.getType().isAssignableFrom(Integer.class))
                    {
                        byteBuffer.put(this.getBytesFromInteger((Integer) fieldObject, type), 0, type.length());
                    }
                    else if (field.getType().isAssignableFrom(int.class))
                    {
                        byteBuffer.put(this.getBytesFromInteger((Integer) fieldObject, type), 0, type.length());
                    }
                    else if (field.getType().isAssignableFrom(Long.class))
                    {
                        byteBuffer.put(this.getBytesFromLong((Long) fieldObject, type), 0, type.length());
                    }
                    else if (field.getType().isAssignableFrom(long.class))
                    {
                        byteBuffer.put(this.getBytesFromLong((Long) fieldObject, type), 0, type.length());
                    }
                    else if (field.getType().isAssignableFrom(BigInteger.class))
                    {
                        byteBuffer.put(this.getBytesFromBigInteger((BigInteger) fieldObject, type), 0, type.length());
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

    private final byte[] getBytesFromString(String field, final OFTPType type)
            throws UnsupportedEncodingException
    {
        byte[] byteArray;
        String format = "%-" + String.format("%d", type.length()) + "S";
        byteArray = String.format(format, field).getBytes(Command.CODEPAGE);
        return byteArray;
    }

    private final byte[] getBytesFromLong(final Long field, final OFTPType type)
            throws UnsupportedEncodingException
    {
        String format = "%0" + String.format("%d", type.length()) + "d";
        return this.getBytesFromString(String.format(format, field), type);
    }

    private final byte[] getBytesFromInteger(final Integer field, final OFTPType type)
            throws UnsupportedEncodingException
    {
        String format = "%0" + String.format("%d", type.length()) + "d";
        return this.getBytesFromString(String.format(format, field), type);
    }

    private final byte[] getBytesFromBigInteger(final BigInteger field, final OFTPType type)
            throws UnsupportedEncodingException
    {
        String format = "%0" + String.format("%d", type.length()) + "d";
        return this.getBytesFromString(String.format(format, field), type);
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
                        stringBuffer.append(field.get(this));
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

