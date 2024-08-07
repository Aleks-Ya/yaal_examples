package security.cert.key;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import security.SecurityHelper;

import java.security.KeyStoreException;
import java.security.cert.Certificate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Working with a Keys.
 */
class KeyTest {

    @Test
    void generateKeyPair() {
        var keyPair = SecurityHelper.generateKeyPair();
        var publicKey = keyPair.getPublic();
        var privateKey = keyPair.getPrivate();
        assertThat(publicKey).isNotNull();
        assertThat(privateKey).isNotNull();
    }

    @Test
    void getPublicKeyFromCertificate() {
        var certificate = SecurityHelper.readCertificateFromResource("security/certificate.crt");
        var pubKey = certificate.getPublicKey();
        assertThat(pubKey).isNotNull();
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

        assertThat(pubKey).isNotNull();
        assertThat(priKey).isNotNull();
    }
}
