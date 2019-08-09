package security.certificate;

import org.junit.Test;
import security.SecurityHelper;
import sun.security.pkcs10.PKCS10;
import sun.security.x509.X500Name;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.KeyPair;
import java.security.Signature;

import static org.junit.Assert.assertNotNull;

public class CertificateSingRequestTest {

    @Test
    public void generateKeyPair() {
        KeyPair pair = SecurityHelper.generateKeyPair();
        byte[] csrData = generateCSR("SHA256WithRSA", pair);
        assertNotNull(csrData);
    }

    private static byte[] generateCSR(String sigAlg, KeyPair keyPair) {
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outStream);
            X500Name x500Name = new X500Name("CN=EXAMPLE.COM");
            Signature sig = Signature.getInstance(sigAlg);
            sig.initSign(keyPair.getPrivate());
            PKCS10 pkcs10 = new PKCS10(keyPair.getPublic());
            pkcs10.encodeAndSign(x500Name, sig);
            pkcs10.print(printStream);
            return outStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
