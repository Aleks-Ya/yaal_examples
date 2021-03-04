package controllers.ssl

import controllers.ssl.SslController.BODY
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.scalatest.TestData
import org.scalatest.time.SpanSugar.convertIntToGrainOfTime
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.{Application, Configuration}
import play.api.http.Status.OK
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.ws.WSClient

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.Source

class SslSpec extends PlaySpec with GuiceOneServerPerTest {

//  implicit override def newAppForTest(testData: TestData): Application = {
//    new GuiceApplicationBuilder().configure(Map(
//      "a" -> "b"
//      "play.server.https.keyStore.path" -> "/home/aleks/pr/home/yaal_examples/Java+/JEE+/Jetty/resources/handler/ssl/server_keystore.jks",
//      "play.server.https.keyStore.type" -> "JKS",
//      "play.server.https.keyStore.password" -> "098765",
//      "http.port" -> "disabled",
//      "https.port" -> "9443",
//      "play.ws.ssl.trustManager.stores.0.type" -> "JKS",
//      "play.ws.ssl.trustManager.stores.0.path" -> "/home/aleks/pr/home/yaal_examples/Java+/JEE+/Jetty/resources/handler/ssl/client_truststore.jks",
//      "play.ws.ssl.trustManager.stores.0.password" -> "654321",
//    )).build()
//  }

  "Test" should {
//    "override FakeApplication" in {
//      app.configuration.getOptional[String]("http.port") mustBe Some("disabled")
//    }

    "setup SSL connection" in {
      val endpoint = runningServer.endpoints.httpEndpoint.get
      println("Endpoint port: " + endpoint.port)
//      Thread.sleep(60*1000)
//      val wsClient = app.injector.instanceOf[WSClient]
      val url = s"http://localhost:${endpoint.port}/ssl"
      println("URL: " + url)
//      val future = wsClient.url(url)
//        .withRequestTimeout(10000.millis)
//        .withFollowRedirects(true)
//        .get()
//      val response = Await.result(future, Duration.Inf)
//      response.status mustBe OK
      val httpClient = HttpClients.createDefault
      val httpGet = new HttpGet(url)
      val response = httpClient.execute(httpGet)
      response.getStatusLine.getStatusCode mustBe OK
      Source.fromInputStream(response.getEntity.getContent).mkString mustBe BODY
    }
  }
}
