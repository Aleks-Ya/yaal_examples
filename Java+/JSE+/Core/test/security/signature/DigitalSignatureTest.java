package security.signature;

import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Digital signature.
 */
public class DigitalSignatureTest {

    @Test
    public void rsaEncryptDecrypt() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        System.out.println(Arrays.toString(privateKey.getEncoded()));
        System.out.println(Arrays.toString(publicKey.getEncoded()));

        String inputStr = "my data";
        byte[] inputBytes = inputStr.getBytes();
        System.out.println(Arrays.toString(inputBytes));

        Signature signature = Signature.getInstance("SHA256WithRSA");
        signature.initSign(privateKey);
        signature.update(inputBytes);
        byte[] signatureBytes = signature.sign();

        Signature verificationSignature = Signature.getInstance("SHA256WithRSA");
        verificationSignature.initVerify(publicKey);
        verificationSignature.update(inputBytes);
        boolean matchers = verificationSignature.verify(signatureBytes);
        assertTrue(matchers);
    }
}
