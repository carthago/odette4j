package com.de.grossmann.carthago.protocol.odette;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509TrustManager;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class OFTPTrustManagerFactory extends TrustManagerFactorySpi {

    private static final Logger LOGGER;
    private static final TrustManager DUMMY_TRUST_MANAGER;

    static {

        LOGGER = LoggerFactory.getLogger(OFTPTrustManagerFactory.class);
        DUMMY_TRUST_MANAGER = new X509TrustManager() {

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            @Override
            public void checkClientTrusted(
                    X509Certificate[] chain, String authType) throws CertificateException {
                // Always trust - it is an example.
                LOGGER.warn("We do not check the client certificate: " + chain[0].getSubjectDN());
            }

            @Override
            public void checkServerTrusted(
                    X509Certificate[] chain, String authType) throws CertificateException {
                // Always trust - it is an example.
                LOGGER.warn("We do not check the server certificate: " + chain[0].getSubjectDN());
            }
        };
    }

    public static TrustManager[] getTrustManagers() {
        return new TrustManager[]{DUMMY_TRUST_MANAGER};
    }

    @Override
    protected TrustManager[] engineGetTrustManagers() {
        return getTrustManagers();
    }

    @Override
    protected void engineInit(KeyStore keystore) throws KeyStoreException {
        // Unused
    }

    @Override
    protected void engineInit(ManagerFactoryParameters managerFactoryParameters)
            throws InvalidAlgorithmParameterException {
        // Unused
    }
}