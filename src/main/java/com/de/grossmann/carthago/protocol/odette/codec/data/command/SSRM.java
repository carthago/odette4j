package com.de.grossmann.carthago.protocol.odette.codec.data.command;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import com.de.grossmann.carthago.common.ByteUtils;


public class SSRM implements Command {

    private final CommandIdentifier  ssrmcmd  = CommandIdentifier.SSRM;
    private final String ssrmmsg  = "ODETTE FTP READY ";
    private final char  ssrmcr   = 0x0D;

    /* (non-Javadoc)
     * @see com.de.grossmann.carthago.protocol.odette.codec.data.command.Command#getCommandIdentifier()
     */
    @Override
    public CommandIdentifier getCommandIdentifier() {
        return this.ssrmcmd;
    }
    
    /* (non-Javadoc)
     * @see com.de.grossmann.carthago.protocol.odette.codec.data.command.Command#initialize(byte[])
     */
    @Override
    public void initialize(byte[] odetteExchangeBuffer) {
        if (odetteExchangeBuffer != null && !Arrays.equals(getBytes(), odetteExchangeBuffer))
        {
            System.err.println("FOO: Received odette exchange buffer (ssrm) is not valid!");
            System.out.println("SSRM.initialize() - odetteExchangeBuffer = " + ByteUtils.bytesToHex(odetteExchangeBuffer));
            System.out.println("SSRM.initialize() - ssrm.getBytes()      = " + ByteUtils.bytesToHex(getBytes()));
        }
    }

    /* (non-Javadoc)
     * @see com.de.grossmann.carthago.protocol.odette.codec.data.command.Command#getBytes()
     */
    @Override
    public byte[] getBytes() {
        
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream( byteArrayOutputStream );

        try
        {
            dataOutputStream.write(this.ssrmcmd.getIdentifier()); 
            dataOutputStream.write(this.ssrmmsg.getBytes(Charset.forName("ASCII")));
            dataOutputStream.write(this.ssrmcr);
            dataOutputStream.close();
        }
        catch (IOException e)
        {
            System.err.println("FOO: " + e.getMessage());
        }
        
        try
        {
            dataOutputStream.close();
            byteArrayOutputStream.close();
        }
        catch (IOException e)
        {
            System.err.println("FOO: " + e.getMessage());
        }
        
        return byteArrayOutputStream.toByteArray();
    }

    /* (non-Javadoc)
     * @see com.de.grossmann.carthago.protocol.odette.codec.data.command.Command#getLength()
     */
    @Override
    public int getLength() {
        return getBytes().length;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return String.format("SSRM [ssrmcmd=%s, ssrmmsg=%s, ssrmcr=%s]", ssrmcmd, ssrmmsg, ssrmcr);
    }
}
