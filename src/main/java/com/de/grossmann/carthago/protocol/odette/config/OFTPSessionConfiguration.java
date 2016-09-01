package com.de.grossmann.carthago.protocol.odette.config;

public class OFTPSessionConfiguration
{

    private Long level = 5L;
    private String userCode;
    private String password;
    private Long      dataExchangeBufferSize = 4096L;
    private Character capabilities           = 'B';
    private Character compression            = 'N';
    private Character restart                = 'N';
    private Character specialLogic           = 'N';
    private Long      credit                 = 99L;
    private Character authentication         = 'N';
    private String    reserved               = "";
    private String    userData               = "Dummy";
    private Character cr                     = '\r';

    public OFTPSessionConfiguration(final String userCode, final String password)
    {

        this.userCode = userCode;
        this.password = password;

    }

    public Long getLevel()
    {
        return level;
    }

    public void setLevel(Long level) { this.level = level; }

    public String getUserCode()
    {
        return userCode;
    }

    public void setUserCode(String userCode)
    {
        this.userCode = userCode;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Long getDataExchangeBufferSize()
    {
        return dataExchangeBufferSize;
    }

    public void setDataExchangeBufferSize(Long dataExchangeBufferSize)
    {
        this.dataExchangeBufferSize = dataExchangeBufferSize;
    }

    public Character getCapabilities()
    {
        return capabilities;
    }

    public void setCapabilities(Character capabilities)
    {
        this.capabilities = capabilities;
    }

    public Character getCompression()
    {
        return compression;
    }

    public void setCompression(Character compression)
    {
        this.compression = compression;
    }

    public Character getRestart()
    {
        return restart;
    }

    public void setRestart(Character restart)
    {
        this.restart = restart;
    }

    public Character getSpecialLogic()
    {
        return specialLogic;
    }

    public void setSpecialLogic(Character specialLogic)
    {
        this.specialLogic = specialLogic;
    }

    public Long getCredit()
    {
        return credit;
    }

    public void setCredit(Long credit)
    {
        this.credit = credit;
    }

    public Character getAuthentication()
    {
        return authentication;
    }

    public void setAuthentication(Character authentication)
    {
        this.authentication = authentication;
    }

    public String getReserved()
    {
        return reserved;
    }

    public void setReserved(String reserved)
    {
        this.reserved = reserved;
    }

    public String getUserData()
    {
        return userData;
    }

    public void setUserData(String userData)
    {
        this.userData = userData;
    }

    public Character getCr()
    {
        return cr;
    }

    public void setCr(Character cr)
    {
        this.cr = cr;
    }
}
