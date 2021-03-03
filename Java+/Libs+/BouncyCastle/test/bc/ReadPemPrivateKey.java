package bc;

import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
import org.junit.Test;
import util.ResourceUtil;

import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ReadPemPrivateKey {

    @Test
    public void readKeyPair() throws IOException {
        var keyFile = ResourceUtil.resourceToFile(ReadPemPrivateKey.class, "rsa-private.pem");
        var pemParser = new PEMParser(new FileReader(keyFile));
        var keyPair = (PEMKeyPair) pemParser.readObject();
        var privateKeyInfo = keyPair.getPrivateKeyInfo();
        var publicKeyInfo = keyPair.getPublicKeyInfo();
        assertThat(privateKeyInfo, notNullValue());
        assertThat(publicKeyInfo, notNullValue());
    }

    @Test
    public void readKeyPairPKCS8EncryptedPrivateKeyInfo() throws IOException {
        var keyFile = ResourceUtil.resourceToFile(ReadPemPrivateKey.class, "rsa-pkcs8-private.pem");
        var pemParser = new PEMParser(new FileReader(keyFile));
        var keyPair = (PKCS8EncryptedPrivateKeyInfo) pemParser.readObject();
        var encoded = keyPair.getEncoded();
        assertThat(encoded.length, equalTo(2481));
    }

}
