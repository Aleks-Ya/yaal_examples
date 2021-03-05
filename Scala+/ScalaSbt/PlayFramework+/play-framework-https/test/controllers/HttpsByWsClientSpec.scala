package controllers

import controllers.HomeController.HOME_BODY
import controllers.ServerConfiguration.SERVER_CONFIGURATION_OVERRIDE
import org.scalatest.TestData
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.http.Status.OK
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.ws.WSClient
import play.api.test.{DefaultTestServerFactory, RunningServer}
import play.api.{Application, Configuration}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * Configure HTTPS on server and test it with WSClient.
 */
class HttpsByWsClientSpec extends PlaySpec with GuiceOneServerPerTest {

  implicit override def newAppForTest(testData: TestData): Application = new GuiceApplicationBuilder().configure(Map(
    "play.ws.ssl.trustManager.stores.0.type" -> "JKS",
    "play.ws.ssl.trustManager.stores.0.path" -> getClass.getClassLoader.getResource("client_truststore.jks").getFile,
    "play.ws.ssl.trustManager.stores.0.password" -> "654321",
  )).build()

  implicit override def newServerForTest(app: Application, testData: TestData): RunningServer = new DefaultTestServerFactory() {
    override protected def overrideServerConfiguration(app: Application): Configuration = SERVER_CONFIGURATION_OVERRIDE
  }.start(app)

  "Test" should {
    "setup SSL connection (WSClient)" in {
      val endpoint = runningServer.endpoints.httpsEndpoint.get
      val url = s"https://localhost:${endpoint.port}"
      val wsClient = app.injector.instanceOf[WSClient]
      val future = wsClient.url(url).get()
      val response = Await.result(future, Duration.Inf)
      response.status mustBe OK
      response.body mustBe HOME_BODY
    }
  }
}
