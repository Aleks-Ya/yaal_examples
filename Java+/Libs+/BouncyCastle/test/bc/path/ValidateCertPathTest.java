package bc.path;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Test;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.ResourceUtil.resourceToInputStream;

/**
 * Working with Certification Path.
 * <p>
 * Create certificates:
 * <pre>
 * # Output dir
 * export OUT=/tmp/certpath
 * mkdir -p $OUT
 * cd $OUT
 *
 * # Generate private keys
 * openssl genrsa -out ca-root-private.pem
 * openssl genrsa -out ca-intermediate-private.pem
 * openssl genrsa -out server-private.pem
 *
 * # Create CaRoot self-signed certificate
 * echo "authorityKeyIdentifier=keyid,issuer
 * 	basicConstraints=CA:TRUE
 * 	keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment, keyCertSign" > ca-root-v3.ext
 * openssl req -new -key ca-root-private.pem -out ca-root.csr -subj '/C=RU/ST=Moscow Region/L=Moscow/O=Home/CN=Ca Root'
 * openssl x509 -req -days 365 -in ca-root.csr -signkey ca-root-private.pem -out ca-root.crt -extfile ca-root-v3.ext
 *
 * # Create IntermediateRoot certificate
 * echo "authorityKeyIdentifier=keyid,issuer
 * 	basicConstraints=CA:TRUE
 * 	keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment, keyCertSign" > ca-intermediate-v3.ext
 * openssl req -new -key ca-intermediate-private.pem -out ca-intermediate.csr -subj '/C=RU/ST=Moscow Region/L=Moscow/O=Home/CN=Ca Intermediate'
 * openssl x509 -req -days 365 -in ca-intermediate.csr -CA ca-root.crt -CAkey ca-root-private.pem -out ca-intermediate.crt -CAcreateserial -extfile ca-intermediate-v3.ext
 *
 * # Create Server certificate
 * echo "authorityKeyIdentifier=keyid,issuer
 * 	basicConstraints=CA:FALSE
 * 	keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment" > server-v3.ext
 * openssl req -new -key server-private.pem -out server.csr -subj '/C=RU/ST=Moscow Region/L=Moscow/O=Home/CN=Server'
 * openssl x509 -req -days 365 -in server.csr -CA ca-intermediate.crt -CAkey ca-intermediate-private.pem -out server.crt -CAcreateserial -extfile server-v3.ext
 * </pre>
 */
class ValidateCertPathTest {
    private static final X509Certificate caRootCert = readCertificateFromResource("bc/path/ca-root.crt");
    private static final X509Certificate caIntermediateCert = readCertificateFromResource("bc/path/ca-intermediate.crt");
    private static final X509Certificate serverCert = readCertificateFromResource("bc/path/server.crt");
    private static final String PKIX_ALGORITHM = "PKIX";
    private static final String X509_CERTIFICATE_TYPE = "X.509";
    private static final String COLLECTION_CERT_STORE_TYPE = "Collection";

    @Test
    void validCertPath() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            CertPathBuilderException, NoSuchProviderException, CertPathValidatorException {
        validateCertPath(Set.of(ValidateCertPathTest.caIntermediateCert));
    }

    @Test
    void invalidCertPath() {
        var e = assertThrows(CertPathBuilderException.class, () -> validateCertPath(Set.of()));
        assertThat(e.getMessage(), equalTo("No issuer certificate for certificate in certification path found."));
    }

    private static void validateCertPath(Set<X509Certificate> intermediateCertSet) throws InvalidAlgorithmParameterException,
            NoSuchAlgorithmException, NoSuchProviderException, CertPathBuilderException, CertPathValidatorException {
        Security.addProvider(new BouncyCastleProvider());

        var certSelector = new X509CertSelector();
        certSelector.setBasicConstraints(-2);
        certSelector.setCertificate(serverCert);

        var trustAnchors = Set.of(new TrustAnchor(caRootCert, null));

        var certPathParams = new PKIXBuilderParameters(trustAnchors, certSelector);
        certPathParams.setRevocationEnabled(false);

        var intermediateCertStoreParams = new CollectionCertStoreParameters(intermediateCertSet);
        var intermediateCertStore = CertStore.getInstance(COLLECTION_CERT_STORE_TYPE, intermediateCertStoreParams);
        certPathParams.addCertStore(intermediateCertStore);

        var certPathBuilder = CertPathBuilder.getInstance(PKIX_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
        var certPathBuilderResult = (PKIXCertPathBuilderResult) certPathBuilder.build(certPathParams);
        var certPath = certPathBuilderResult.getCertPath();
        var actCerts = certPath.getCertificates();
        assertThat(actCerts, contains(serverCert, ValidateCertPathTest.caIntermediateCert));

        var validator = CertPathValidator.getInstance(PKIX_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
        var validatorResult = (PKIXCertPathValidatorResult) validator.validate(certPath, certPathParams);
        assertThat(validatorResult.getTrustAnchor().getTrustedCert(), equalTo(caRootCert));
    }

    private static X509Certificate readCertificateFromResource(String resource) {
        try {
            var certificateFactory = CertificateFactory.getInstance(X509_CERTIFICATE_TYPE);
            var certificateInputStream = resourceToInputStream(resource);
            return (X509Certificate) certificateFactory.generateCertificate(certificateInputStream);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
    }
}
