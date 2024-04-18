package http4sdsl

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import fs2.text.utf8.decode
import org.http4s.HttpRoutes
import org.http4s.client.dsl.io._
import org.http4s.dsl.io._
import org.http4s.implicits.http4sLiteralsSyntax
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

/**
 * Use fake server `HttpRoutes` for testing requests.
 */
class HttpRotesTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "HttpRoutes" - {
    "GET body" in {
      val service = HttpRoutes.of[IO] {
        case _ => Ok(IO("abc"))
      }
      val request = GET(uri"/")
      val response = service.orNotFound.run(request)
      response.map(_.body.through(decode).compile.string).flatten
    }.asserting(_ shouldEqual "abc")
  }
}
