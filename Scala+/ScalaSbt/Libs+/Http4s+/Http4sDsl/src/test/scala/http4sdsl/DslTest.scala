package http4sdsl

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.http4s.client.dsl.io._
import org.http4s.dsl.io._
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.implicits.http4sLiteralsSyntax
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers


class DslTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  private val clientResource = EmberClientBuilder.default[IO].build

  "HttpRoutes" - {
    "GET status" in {
      val request = GET(uri"http://httpbin.io/status/201")
      clientResource.use(client =>
        for {
          res <- client.status(request)
        } yield res.code
      ).asserting(_ shouldBe 201)
    }

    "GET request body" in {
      val request = GET(uri"http://httpbin.io/base64/decode/YWJjCg==")
      clientResource.use(client =>
        for {
          body <- client.expect[String](request)
        } yield body
      ).asserting(_ shouldEqual "abc\n")
    }

    "handle error during request" in {
      val request = GET(uri"http://httpbin.io/status/403")
      clientResource.use(client =>
        for {
          body <- client.expectOr[String](request) { response =>
            IO(new IllegalStateException(s"Not allowed: ${response.status.code}"))
          }
        } yield body
      ).assertThrowsWithMessage[IllegalStateException]("Not allowed: 403")
    }
  }
}
