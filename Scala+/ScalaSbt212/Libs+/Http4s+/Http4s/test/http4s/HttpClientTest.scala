package http4s

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.http4s.Method.GET
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.implicits.http4sLiteralsSyntax
import org.http4s.{Request, Status, Uri}
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class HttpClientTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  private val client = EmberClientBuilder.default[IO].build
  "HttpClient sends GET request" - {

    def getStatusCode(url: String) = client.use(c => {
      c.get(url)(response => IO(response.status.code))
    })

    "200 OK" in {
      getStatusCode("https://httpbin.org/status/200")
    }.asserting(_ shouldEqual 200)

    "expect 404 NOT FOUND" in {
      getStatusCode("https://httpbin.org/status/404")
    }.asserting(_ shouldEqual 404)

    "ignore 404 NOT FOUND" in {
      val url = uri"https://httpbin.org/status/404"
      val request = Request[IO](GET, url)
      val response = client.use(c => c.run(request).use { response =>
        if (response.status == Status.NotFound) IO.pure(None)
        else IO.pure(Some(response))
      })
      response.asserting(_ shouldBe None)
    }

    "handle different status codes" in {
      val url = uri"https://httpbin.org/status/500"
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

    def getBodyOption(url: String) = {
      val uri = Uri.unsafeFromString(url)
      val request = Request[IO](GET, uri)
      val body = client.use(c => c.run(request).use { response =>
        response.status match {
          case Status.NotFound => IO.pure(None)
          case Status.InternalServerError => IO.raiseError(new RuntimeException("Internal Server Error"))
          case _ => response.bodyText.compile.string.map(Some(_))
        }
      })
      body
    }

    "return optional body (Some)" in {
      val body: IO[Option[String]] = getBodyOption("https://httpbin.io/base64/decode/YWJjCg==")
      body.asserting(_ shouldEqual Some("abc\n"))
    }

    "return optional body (None 404)" in {
      val body: IO[Option[String]] = getBodyOption("https://httpbin.org/status/404")
      body.asserting(_ shouldEqual None)
    }

    "return optional body (Exception 500)" in {
      val body: IO[Option[String]] = getBodyOption("https://httpbin.org/status/500")
      body.assertThrowsWithMessage[RuntimeException]("Internal Server Error")
    }
  }
}
