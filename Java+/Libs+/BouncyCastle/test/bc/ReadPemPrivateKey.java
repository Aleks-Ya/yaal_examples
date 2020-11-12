package bc;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.junit.Test;
import util.ResourceUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class ReadPemPrivateKey {

    @Test
    public void readKeyPair() throws IOException {
        File keyFile = ResourceUtil.resourceToFile(ReadPemPrivateKey.class, "rsa-private.pem");
        PEMParser pemParser = new PEMParser(new FileReader(keyFile));
        PEMKeyPair keyPair = (PEMKeyPair) pemParser.readObject();
        PrivateKeyInfo privateKeyInfo = keyPair.getPrivateKeyInfo();
        SubjectPublicKeyInfo publicKeyInfo = keyPair.getPublicKeyInfo();
        assertThat(privateKeyInfo, notNullValue());
        assertThat(publicKeyInfo, notNullValue());
    }

}
