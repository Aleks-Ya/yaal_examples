package security.signature;

import org.junit.jupiter.api.Test;

import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Digital signature.
 */
class DigitalSignatureTest {

    @Test
    void rsaEncryptDecrypt() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        var keyPair = keyPairGenerator.generateKeyPair();
        var privateKey = keyPair.getPrivate();
        var publicKey = keyPair.getPublic();
        System.out.println(Arrays.toString(privateKey.getEncoded()));
        System.out.println(Arrays.toString(publicKey.getEncoded()));

        var inputStr = "my data";
        var inputBytes = inputStr.getBytes();
        System.out.println(Arrays.toString(inputBytes));

        var signature = Signature.getInstance("SHA256WithRSA");
        signature.initSign(privateKey);
        signature.update(inputBytes);
        var signatureBytes = signature.sign();

        var verificationSignature = Signature.getInstance("SHA256WithRSA");
        verificationSignature.initVerify(publicKey);
        verificationSignature.update(inputBytes);
        var matchers = verificationSignature.verify(signatureBytes);
        assertThat(matchers).isTrue();
    }
}
