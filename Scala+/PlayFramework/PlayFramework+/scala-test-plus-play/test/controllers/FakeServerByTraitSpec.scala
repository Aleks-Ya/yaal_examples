package controllers

import controllers.GetController.BODY
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.http.Status.OK

import scala.io.Source

/**
 * Creating Server by GuiceOneServerPerTest trait.
 * Requires Java 1.8.
 */
class FakeServerByTraitSpec extends PlaySpec with GuiceOneServerPerTest {

  "Test" should {
    "run Server" in {
      app.configuration.getOptional[String]("play.assets.urlPrefix") mustBe Some("/assets")
      val httpEndpoint = runningServer.endpoints.httpEndpoint.get
      val httpClient = HttpClients.createDefault
      val url = s"http://localhost:${httpEndpoint.port}/text"
      val httpGet = new HttpGet(url)
      val response = httpClient.execute(httpGet)
      response.getStatusLine.getStatusCode mustBe OK
      Source.fromInputStream(response.getEntity.getContent).mkString mustBe BODY
    }
  }
}
