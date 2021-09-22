package security.cert.keystore;

import org.junit.jupiter.api.Test;
import security.SecurityHelper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;

/**
 * Working with a KeyStore.
 */
class KeyStoreTest {
    private static final String CERT_RESOURCE = "security/certificate.crt";

    @Test
    void initEmptyKeyStore() throws KeyStoreException {
        var keyStore = SecurityHelper.initEmptyKeyStore();
        assertThat(Collections.list(keyStore.aliases()), empty());
    }

    @Test
    void addCertificateToKeyStore() throws KeyStoreException {
        var keyStore = SecurityHelper.initEmptyKeyStore();
        var certificate = SecurityHelper.readCertificateFromResource(CERT_RESOURCE);
        var certificateAlias = "my_cert";
        keyStore.setCertificateEntry(certificateAlias, certificate);

        assertThat(Collections.list(keyStore.aliases()), contains(certificateAlias));

        var actCertificate = keyStore.getCertificate(certificateAlias);
        assertThat(actCertificate, equalTo(certificate));
    }

    @Test
    void addPrivateKeyToKeyStore() throws NoSuchAlgorithmException, KeyStoreException,
            UnrecoverableKeyException {
        var keyStore = SecurityHelper.initEmptyKeyStore();
        var certificate = SecurityHelper.readCertificateFromResource(CERT_RESOURCE);
        Certificate[] certificateChain = {certificate};

        var keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(1024);
        var privateKey = keyGenerator.generateKeyPair().getPrivate();
        var keyAlias = "my_alias";
        var keyPass = "key pass".toCharArray();
        keyStore.setKeyEntry(keyAlias, privateKey, keyPass, certificateChain);

        assertThat(Collections.list(keyStore.aliases()), contains(keyAlias));

        var actKey = keyStore.getKey(keyAlias, keyPass);
        assertThat(actKey, equalTo(privateKey));
    }

    @Test
    void saveKeyStoreToFile() throws NoSuchAlgorithmException, KeyStoreException,
            IOException, CertificateException {
        var keyStore = SecurityHelper.initEmptyKeyStore();

        var certificate = SecurityHelper.readCertificateFromResource(CERT_RESOURCE);
        var certificateAlias = "my_cert";
        keyStore.setCertificateEntry(certificateAlias, certificate);
        assertThat(Collections.list(keyStore.aliases()), contains(certificateAlias));

        var keyStorePass = "my pass".toCharArray();
        var keyStoreFile = Files.createTempFile("keystore_", "_ks").toFile();
        var keyStoreOutputStream = new FileOutputStream(keyStoreFile);
        keyStore.store(keyStoreOutputStream, keyStorePass);

        var actKeyStore = KeyStore.getInstance("JKS");
        var keyStoreInputStream = new FileInputStream(keyStoreFile);
        actKeyStore.load(keyStoreInputStream, keyStorePass);
        assertThat(Collections.list(actKeyStore.aliases()), contains(certificateAlias));
    }

}
