package azure.certificate.web_signed;

import com.microsoft.aad.msal4j.ClientCredentialFactory;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class CreateCredentialFromCertTest {

    @Test
    public void createFromCert() throws IOException, CertificateException, UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException {
        var certFile = new FileInputStream(CreateCredentialFromCertTest.class.getResource("msal-web-signed.p12").getFile());
        var certPassword = "";
        var cert = ClientCredentialFactory.createFromCertificate(certFile, certPassword);
        var privateKey = cert.privateKey();
        assertThat(privateKey, notNullValue());
    }
}