package http4s

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.http4s.ember.client.EmberClientBuilder
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class HttpClientTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  private val client = EmberClientBuilder.default[IO].build
  "HttpClient" - {
    "send GET request" in {
      client.use(c => c.get("https://http4s.org/")(response => IO(response.status.code)))
    }.asserting(_ shouldEqual 200)
  }
}
