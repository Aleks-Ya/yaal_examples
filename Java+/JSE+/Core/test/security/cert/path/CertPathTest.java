package security.cert.path;

import org.junit.Ignore;
import org.junit.Test;
import security.SecurityHelper;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.X509CertSelector;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Working with a KeyStore.
 */
public class CertPathTest {

    @Test
    @Ignore("Not finished")
    public void getCertPath() throws KeyStoreException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, CertPathBuilderException {
        var certPathBuilder = CertPathBuilder.getInstance("PKCS12");
        var keyStore = SecurityHelper.initEmptyKeyStore();
        var certSelector = new X509CertSelector();
        var certPathParams = new PKIXBuilderParameters(keyStore, certSelector);
        var certPathBuilderResult = certPathBuilder.build(certPathParams);
        var certPath = certPathBuilderResult.getCertPath();
        assertThat(certPath, notNullValue());
    }
}
