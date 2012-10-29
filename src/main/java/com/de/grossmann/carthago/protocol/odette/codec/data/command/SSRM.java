package com.de.grossmann.carthago.protocol.odette.codec.data.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;


public class SSRM implements Command {

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(SSRM.class);
    }

    private final CommandIdentifier ssrmcmd = CommandIdentifier.SSRM;
    private final String ssrmmsg = "ODETTE FTP READY ";
    private final char ssrmcr = 0x0D;

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
        if (odetteExchangeBuffer != null && !Arrays.equals(getBytes(), odetteExchangeBuffer)) {
            LOGGER.error("Invalid SSRM command received.");
        }
    }

    /* (non-Javadoc)
     * @see com.de.grossmann.carthago.protocol.odette.codec.data.command.Command#getBytes()
     */
    @Override
    public byte[] getBytes() {

        byte[] bytes = null;

        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream)
        ) {
            dataOutputStream.write(this.ssrmcmd.getIdentifier());
            dataOutputStream.write(this.ssrmmsg.getBytes(Charset.forName("ASCII")));
            dataOutputStream.write(this.ssrmcr);

            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            LOGGER.error("Unable to retrieve bytes from SSRM command.");  //To change body of catch statement use File | Settings | File Templates.
        }

        return bytes;
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
    public String toString() {
        return String.format("SSRM [ssrmcmd=%s, ssrmmsg=%s, ssrmcr=%s]", ssrmcmd, ssrmmsg, ssrmcr);
    }
}
