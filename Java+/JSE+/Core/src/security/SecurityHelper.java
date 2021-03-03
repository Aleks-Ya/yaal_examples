package security;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import static util.ResourceUtil.resourceToInputStream;

public final class SecurityHelper {
    public static KeyStore initEmptyKeyStore() {
        try {
            var keyStore = KeyStore.getInstance("JKS");
            keyStore.load(null, null);
            return keyStore;
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException(e);
        }
    }

    public static KeyPair generateKeyPair() {
        try {
            var keyGenerator = KeyPairGenerator.getInstance("RSA");
            keyGenerator.initialize(1024);
            return keyGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static Certificate readCertificateFromResource(String resource) {
        try {
            var certificateFactory = CertificateFactory.getInstance("X.509");
            var certificateInputStream = resourceToInputStream(resource);
            return certificateFactory.generateCertificate(certificateInputStream);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
    }
}
