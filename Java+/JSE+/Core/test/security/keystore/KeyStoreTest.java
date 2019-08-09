package security.keystore;

import org.junit.Test;
import security.SecurityHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Key;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Collections;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Working with a KeyStore.
 */
public class KeyStoreTest {

    @Test
    public void initEmptyKeyStore() throws KeyStoreException {
        KeyStore keyStore = SecurityHelper.initEmptyKeyStore();
        assertThat(Collections.list(keyStore.aliases()), empty());
    }

    @Test
    public void addCertificateToKeyStore() throws KeyStoreException, CertificateException {
        KeyStore keyStore = SecurityHelper.initEmptyKeyStore();
        Certificate certificate = SecurityHelper.readCertificateFromFile();
        String certificateAlias = "my_cert";
        keyStore.setCertificateEntry(certificateAlias, certificate);

        assertThat(Collections.list(keyStore.aliases()), contains(certificateAlias));

        Certificate actCertificate = keyStore.getCertificate(certificateAlias);
        assertThat(actCertificate, equalTo(certificate));
    }

    @Test
    public void addPrivateKeyToKeyStore() throws NoSuchAlgorithmException, KeyStoreException,
            UnrecoverableKeyException, CertificateException {
        KeyStore keyStore = SecurityHelper.initEmptyKeyStore();
        Certificate certificate = SecurityHelper.readCertificateFromFile();
        Certificate[] certificateChain = {certificate};

        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(1024);
        PrivateKey privateKey = keyGenerator.generateKeyPair().getPrivate();
        String keyAlias = "my_alias";
        char[] keyPass = "key pass".toCharArray();
        keyStore.setKeyEntry(keyAlias, privateKey, keyPass, certificateChain);

        assertThat(Collections.list(keyStore.aliases()), contains(keyAlias));

        Key actKey = keyStore.getKey(keyAlias, keyPass);
        assertThat(actKey, equalTo(privateKey));
    }

    @Test
    public void saveKeyStoreToFile() throws NoSuchAlgorithmException, KeyStoreException,
            IOException, CertificateException {
        KeyStore keyStore = SecurityHelper.initEmptyKeyStore();

        Certificate certificate = SecurityHelper.readCertificateFromFile();
        String certificateAlias = "my_cert";
        keyStore.setCertificateEntry(certificateAlias, certificate);
        assertThat(Collections.list(keyStore.aliases()), contains(certificateAlias));

        char[] keyStorePass = "my pass".toCharArray();
        File keyStoreFile = Files.createTempFile("keystore_", "_ks").toFile();
        FileOutputStream keyStoreOutputStream = new FileOutputStream(keyStoreFile);
        keyStore.store(keyStoreOutputStream, keyStorePass);

        KeyStore actKeyStore = KeyStore.getInstance("JKS");
        FileInputStream keyStoreInputStream = new FileInputStream(keyStoreFile);
        actKeyStore.load(keyStoreInputStream, keyStorePass);
        assertThat(Collections.list(actKeyStore.aliases()), contains(certificateAlias));
    }

}
