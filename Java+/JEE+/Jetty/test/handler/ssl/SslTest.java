package handler.ssl;

import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.junit.Test;
import util.ResourceUtil;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * HTTPS connection with Jetty + HttpClient.<br/>
 * Docs: <a href="https://www.eclipse.org/jetty/documentation/jetty-11/programming-guide/index.html#pg-server-http-connector-protocol-http11-tls">Encrypted HTTP/1.1 (https)</a><br/>
 * Generate certificates:
 * <pre>
 * # Output dir
 * export OUT=/tmp/jetty/certpath
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
 * openssl x509 -req -days 365 -in ca-root.csr -signkey ca-root-private.pem -outform PEM -out ca-root.pem -extfile ca-root-v3.ext
 *
 * # Create IntermediateRoot certificate
 * echo "authorityKeyIdentifier=keyid,issuer
 * 	basicConstraints=CA:TRUE
 * 	keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment, keyCertSign" > ca-intermediate-v3.ext
 * openssl req -new -key ca-intermediate-private.pem -out ca-intermediate.csr -subj '/C=RU/ST=Moscow Region/L=Moscow/O=Home/CN=Ca Intermediate'
 * openssl x509 -req -days 365 -in ca-intermediate.csr -CA ca-root.pem -CAkey ca-root-private.pem -out ca-intermediate.pem -outform PEM -CAcreateserial -extfile ca-intermediate-v3.ext
 *
 * # Create Server certificate
 * echo "authorityKeyIdentifier=keyid,issuer
 * 	basicConstraints=CA:FALSE
 * 	keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment
 * 	subjectAltName=DNS:localhost,IP:127.0.0.1" > server-v3.ext
 * openssl req -new -key server-private.pem -out server.csr -subj '/C=RU/ST=Moscow Region/L=Moscow/O=Home/CN=Server'
 * openssl x509 -req -days 365 -in server.csr -CA ca-intermediate.pem -CAkey ca-intermediate-private.pem -out server.pem -outform PEM -CAcreateserial -extfile server-v3.ext
 *
 * # Create server_keystore
 * openssl pkcs12 -export -in server.pem -inkey server-private.pem -name localhost -out server_keystore.p12 -CAfile ca-root.crt -caname root -passout pass:456789
 * keytool -importkeystore -deststorepass 098765 -destkeypass 098765 -destkeystore server_keystore.jks -srckeystore server_keystore.p12 -srcstoretype PKCS12 -srcstorepass 456789 -alias localhost
 * keytool -import -noprompt -alias ca-intermediate -file ca-intermediate.pem -keystore server_keystore.jks -storepass 098765
 *
 * # Create client_truststore
 * keytool -import -noprompt -alias ca-root -file ca-root.pem -keystore client_truststore.jks -storepass 654321
 * </pre>
 */
public class SslTest {

    @Test
    public void ssl() throws Exception {
        var server = new Server();
        var expBody = "abc";
        var url = runServer(server, expBody);
        var response = runClient(url);
        server.stop();
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.body(), equalTo(expBody));
    }

    private static HttpResponse<String> runClient(String baseUrl) throws NoSuchAlgorithmException, KeyStoreException,
            IOException, CertificateException, UnrecoverableKeyException, KeyManagementException, InterruptedException {
        var random = SecureRandom.getInstance("SHA1PRNG");
        var keyManagers = getKeyManagers();
        var trustManagers = getTrustManagers();
        var sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagers, trustManagers, random);
        var httpClient = HttpClient.newBuilder().sslContext(sslContext).build();
        var request = HttpRequest.newBuilder().uri(URI.create(baseUrl)).GET().build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static String runServer(Server server, String expBody) throws Exception {
        var httpConfig = new HttpConfiguration();
        httpConfig.addCustomizer(new SecureRequestCustomizer());
        var http11 = new HttpConnectionFactory(httpConfig);

        var keystorePath = ResourceUtil.resourceToPath(SslTest.class, "server_keystore.jks");
        var keystorePassword = "098765";
        var sslContextFactory = new SslContextFactory.Server();
        sslContextFactory.setKeyStorePath(keystorePath);
        sslContextFactory.setKeyStorePassword(keystorePassword);

        var tls = new SslConnectionFactory(sslContextFactory, http11.getProtocol());
        var connector = new ServerConnector(server, tls, http11);
        connector.setPort(8443);

        server.addConnector(connector);

        var handler = new InfoHandler(expBody);
        server.setHandler(handler);
        server.start();

        var port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();
        return "https://localhost:" + port;
    }

    private static KeyManager[] getKeyManagers() throws NoSuchAlgorithmException, KeyStoreException, IOException,
            CertificateException, UnrecoverableKeyException {
        var keyManagerFactory = KeyManagerFactory.getInstance("PKIX");
        var keyStore = KeyStore.getInstance("JKS");
        keyStore.load(null, null);
        keyManagerFactory.init(keyStore, new char[0]);
        return keyManagerFactory.getKeyManagers();
    }

    private static TrustManager[] getTrustManagers() throws NoSuchAlgorithmException, KeyStoreException, IOException,
            CertificateException {
        var trustManagerFactory = TrustManagerFactory.getInstance("PKIX");
        var truststoreFile = ResourceUtil.resourceToFile(SslTest.class, "client_truststore.jks");
        var truststorePassword = "654321".toCharArray();
        var truststore = KeyStore.getInstance(truststoreFile, truststorePassword);
        trustManagerFactory.init(truststore);
        return trustManagerFactory.getTrustManagers();
    }
}