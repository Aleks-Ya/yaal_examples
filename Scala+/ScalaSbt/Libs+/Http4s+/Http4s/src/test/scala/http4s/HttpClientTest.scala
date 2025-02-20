package http4s

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.http4s.Method.GET
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.implicits.http4sLiteralsSyntax
import org.http4s.{Request, Status}
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class HttpClientTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  private val client = EmberClientBuilder.default[IO].build
  "HttpClient sends GET request" - {

    "200 OK" in {
      client.use(c => c.get("http://httpbin.org/status/200")(response => IO(response.status.code)))
    }.asserting(_ shouldEqual 200)

    "expect 404 NOT FOUND" in {
      client.use(c => c.get("http://httpbin.org/status/404")(response => IO(response.status.code)))
    }.asserting(_ shouldEqual 404)

    "ignore 404 NOT FOUND" in {
      val url = uri"http://httpbin.org/status/404"
      val request = Request[IO](GET, url)
      val response = client.use(c => c.run(request).use { response =>
        if (response.status == Status.NotFound) IO.pure(None)
        else IO.pure(Some(response))
      })
      response.asserting(_ shouldBe None)
    }

    "handle different status codes" in {
      val url = uri"http://httpbin.org/status/500"
      val request = Request[IO](GET, url)
      val response = client.use(c => c.run(request).use { response =>
        response.status match {
          case Status.NotFound => IO.pure(None)
          case Status.InternalServerError => IO.raiseError(new RuntimeException("Internal Server Error"))
          case _ => IO.pure(Some(response))
        }
      })
      response.assertThrowsWithMessage[RuntimeException]("Internal Server Error")
    }

    "return optional body" in {
      val url = uri"http://httpbin.io/base64/decode/YWJjCg=="
      val request = Request[IO](GET, url)
      val body = client.use(c => c.run(request).use { response =>
        response.status match {
          case Status.NotFound => null
          case Status.InternalServerError => IO.raiseError(new RuntimeException("Internal Server Error"))
          case _ => response.bodyText.compile.string
        }
      })
      body.asserting(_ shouldEqual "abc\n")
    }
  }
}
