package http4s

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.http4s.Method.GET
import org.http4s.client.middleware.{Retry, RetryPolicy}
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.{Request, Uri}
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.TimeoutException
import scala.concurrent.duration.DurationInt

class RetryTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "HttpClient" - {
    "should do retry" in {
      EmberClientBuilder.default[IO].build.use(client => {
        val backoff = RetryPolicy.exponentialBackoff(1.second, 2)
        val retryPolicy = RetryPolicy[IO](backoff = backoff)
        val retryingClient = Retry(retryPolicy)(client)
        val request = Request[IO](GET, Uri.unsafeFromString(s"https://httpbin.io/status/500"))
        retryingClient.run(request).use { response =>
          IO {
            response.status.code shouldEqual 500
          }
        }
      })
    }
  }
}
