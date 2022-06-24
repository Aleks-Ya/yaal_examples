package bc;

import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.FileReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ReadPemPrivateKeyTest {

    @Test
    void readKeyPair() throws IOException {
        var keyFile = ResourceUtil.resourceToFile(ReadPemPrivateKeyTest.class, "rsa-private.pem");
        var pemParser = new PEMParser(new FileReader(keyFile));
        var keyPair = (PEMKeyPair) pemParser.readObject();
        var privateKeyInfo = keyPair.getPrivateKeyInfo();
        var publicKeyInfo = keyPair.getPublicKeyInfo();
        assertThat(privateKeyInfo).isNotNull();
        assertThat(publicKeyInfo).isNotNull();
    }

    @Test
    void readKeyPairPKCS8EncryptedPrivateKeyInfo() throws IOException {
        var keyFile = ResourceUtil.resourceToFile(ReadPemPrivateKeyTest.class, "rsa-pkcs8-private.pem");
        var pemParser = new PEMParser(new FileReader(keyFile));
        var keyPair = (PKCS8EncryptedPrivateKeyInfo) pemParser.readObject();
        var encoded = keyPair.getEncoded();
        assertThat(encoded.length).isEqualTo(2481);
    }

}
