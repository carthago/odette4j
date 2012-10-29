package com.de.grossmann.carthago.protocol.odette.codec.data.command;


public class SSID implements Command
{
    private CommandIdentifier ssidcmd = CommandIdentifier.SSID;
    
    /* (non-Javadoc)
     * @see com.de.grossmann.carthago.protocol.odette.codec.data.command.Command#getCommandIdentifier()
     */
    @Override
    public CommandIdentifier getCommandIdentifier()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.de.grossmann.carthago.protocol.odette.codec.data.command.Command#initialize(byte[])
     */
    @Override
    public void initialize(byte[] odetteExchangeBuffer)
    {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.de.grossmann.carthago.protocol.odette.codec.data.command.Command#getBytes()
     */
    @Override
    public byte[] getBytes()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.de.grossmann.carthago.protocol.odette.codec.data.command.Command#getLength()
     */
    @Override
    public int getLength()
    {
        // TODO Auto-generated method stub
        return 0;
    }

}
