package controllers

import java.security.{KeyStore, SecureRandom}

import controllers.HomeController.HOME_BODY
import controllers.ServerConfiguration.SERVER_CONFIGURATION_OVERRIDE
import javax.net.ssl._
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.scalatest.TestData
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.http.Status.OK
import play.api.test.{DefaultTestServerFactory, RunningServer}
import play.api.{Application, Configuration}

import scala.io.Source

/**
 * Configure HTTPS on server and test it with Apache HttpClient.
 */
class HttpsByApacheHttpClientsSpec extends PlaySpec with GuiceOneServerPerTest {

  implicit override def newServerForTest(app: Application, testData: TestData): RunningServer = new DefaultTestServerFactory() {
    override protected def overrideServerConfiguration(app: Application): Configuration = SERVER_CONFIGURATION_OVERRIDE
  }.start(app)

  "Test" should {
    "setup SSL connection (Apache HttpClients)" in {
      val endpoint = runningServer.endpoints.httpsEndpoint.get
      val url = s"https://localhost:${endpoint.port}"
      val random: SecureRandom = SecureRandom.getInstance("SHA1PRNG")
      val keyManagers: Array[KeyManager] = getKeyManagers
      val trustManagers: Array[TrustManager] = getTrustManagers
      val sslContext = SSLContext.getInstance("TLS")
      sslContext.init(keyManagers, trustManagers, random)

      val httpClient = HttpClients.custom().setSSLContext(sslContext).build()
      val httpGet = new HttpGet(url)
      val response = httpClient.execute(httpGet)
      response.getStatusLine.getStatusCode mustBe OK
      Source.fromInputStream(response.getEntity.getContent).mkString mustBe HOME_BODY
    }
  }

  private def getKeyManagers = {
    val keyStore = KeyStore.getInstance("JKS")
    keyStore.load(null, null)
    val keyManagerFactory = KeyManagerFactory.getInstance("PKIX")
    keyManagerFactory.init(keyStore, new Array[Char](0))
    keyManagerFactory.getKeyManagers
  }

  private def getTrustManagers = {
    val truststoreFile = getClass.getClassLoader.getResourceAsStream("client_truststore.jks")
    val truststorePassword = "654321".toCharArray
    val truststore = KeyStore.getInstance("JKS")
    truststore.load(truststoreFile, truststorePassword)
    val trustManagerFactory = TrustManagerFactory.getInstance("PKIX")
    trustManagerFactory.init(truststore)
    trustManagerFactory.getTrustManagers
  }
}
