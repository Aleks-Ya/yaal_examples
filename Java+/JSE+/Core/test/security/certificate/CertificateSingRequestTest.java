package security.certificate;

import org.junit.Ignore;
import org.junit.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * Working with a CertificateSpi.
 */
public class CertificateSingRequestTest {
    @Test
    @Ignore("Need Bouncy Castle")
    public void generateKeyPair() throws NoSuchAlgorithmException, CertificateException {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(1024);
        KeyPair pair = keyGenerator.generateKeyPair();

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        
        
//        PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(
//                new X500Principal("C=US, L=Vienna, O=Your Company Inc, CN=yourdomain.com/emailAddress=your@email.com"),
//                pair.getPublic()
//        );
//        JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SHA256withRSA");
//        ContentSigner signer = null;
//        try {
//            signer = csBuilder.build(pair.getPrivate());
//        } catch (OperatorCreationException e) {
//            throw new RuntimeException(e);
//        }
//        PKCS10CertificationRequest csr = p10Builder.build(signer);
     
     
     
     
     
//        PublicKey publicKey = keyPair.getPublic();
//        PrivateKey privateKey = keyPair.getPrivate();
//        assertNotNull(publicKey);
//        assertNotNull(privateKey);
    }

}
