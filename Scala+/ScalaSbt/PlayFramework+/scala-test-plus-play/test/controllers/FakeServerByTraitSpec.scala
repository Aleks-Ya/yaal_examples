package controllers

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.scalatestplus.play._
import org.scalatestplus.play.guice._

/**
 * Creating Server by GuiceOneServerPerTest trait.
 * Requires Java 1.8.
 */
class FakeServerByTraitSpec extends PlaySpec with GuiceOneServerPerTest {

  "Test" should {
    "run Server" in {
      app.configuration.getOptional[String]("play.assets.urlPrefix") mustBe Some("/assets")
      val endpoint = runningServer.endpoints.httpEndpoint.get
      print(endpoint)
      val httpClient = HttpClients.createDefault
      val httpGet = new HttpGet(s"http://${endpoint.host}:$port/text")
      val response = httpClient.execute(httpGet)
      response.getStatusLine.getStatusCode mustBe 400
    }
  }
}
