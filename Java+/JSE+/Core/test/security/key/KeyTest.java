package security.key;

import org.junit.Test;
import security.SecurityHelper;

import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

import static org.junit.Assert.assertNotNull;

/**
 * Working with a Keys.
 */
public class KeyTest {

    @Test
    public void generateKeyPair() {
        KeyPair keyPair = SecurityHelper.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        assertNotNull(publicKey);
        assertNotNull(privateKey);
    }

    @Test
    public void getPublicKeyFromCertificate() {
        Certificate certificate = SecurityHelper.readCertificateFromFile();
        PublicKey pubKey = certificate.getPublicKey();
        assertNotNull(pubKey);
    }

    @Test
    public void storeKeyPairToKeyStore() throws KeyStoreException {
        KeyPair keyPair = SecurityHelper.generateKeyPair();
        KeyStore keyStore = SecurityHelper.initEmptyKeyStore();
        PublicKey pubKey = keyPair.getPublic();
        PrivateKey priKey = keyPair.getPrivate();
        String pubKeyAlias = "pub_key_alias";
        String priKeyAlias = "pri_key_alias";
        keyStore.setKeyEntry(pubKeyAlias, pubKey, null, null);
//        new KeyPair();

        assertNotNull(pubKey);
        assertNotNull(priKey);
    }
}
