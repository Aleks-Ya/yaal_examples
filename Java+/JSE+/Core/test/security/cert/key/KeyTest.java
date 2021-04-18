package security.cert.key;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import security.SecurityHelper;

import java.security.KeyStoreException;
import java.security.cert.Certificate;

import static org.junit.Assert.assertNotNull;

/**
 * Working with a Keys.
 */
public class KeyTest {

    @Test
    public void generateKeyPair() {
        var keyPair = SecurityHelper.generateKeyPair();
        var publicKey = keyPair.getPublic();
        var privateKey = keyPair.getPrivate();
        assertNotNull(publicKey);
        assertNotNull(privateKey);
    }

    @Test
    public void getPublicKeyFromCertificate() {
        var certificate = SecurityHelper.readCertificateFromResource("security/certificate.crt");
        var pubKey = certificate.getPublicKey();
        assertNotNull(pubKey);
    }

    @Test
    @Disabled("Not finished")
    public void storeKeyPairToKeyStore() throws KeyStoreException {
        var keyPair = SecurityHelper.generateKeyPair();
        var keyStore = SecurityHelper.initEmptyKeyStore();
        var pubKey = keyPair.getPublic();
        var priKey = keyPair.getPrivate();
        var pubKeyAlias = "pub_key_alias";
        var priKeyAlias = "pri_key_alias";
        var chain = new Certificate[]{};
        keyStore.setKeyEntry(priKeyAlias, priKey, null, chain);
//        new KeyPair();

        assertNotNull(pubKey);
        assertNotNull(priKey);
    }
}
