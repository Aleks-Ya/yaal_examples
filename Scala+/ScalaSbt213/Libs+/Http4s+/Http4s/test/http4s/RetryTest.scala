package http4s

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import okhttp3.mockwebserver.{MockResponse, MockWebServer}
import org.http4s.Method.GET
import org.http4s.Status.{InternalServerError, Ok, TooManyRequests}
import org.http4s.client.middleware.{Retry, RetryPolicy}
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.{Request, Response, Uri}
import org.scalatest.BeforeAndAfter
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration.DurationInt

class RetryTest extends AsyncFreeSpec with AsyncIOSpec with Matchers with BeforeAndAfter {
  private var server: MockWebServer = _

  before {
    server = new MockWebServer
  }

  after {
    server.shutdown()
  }

  "HttpClient should do retry" - {
    "500 by default" in {
      server.enqueue(new MockResponse().setResponseCode(InternalServerError.code))
      server.enqueue(new MockResponse().setResponseCode(Ok.code))
      server.start()
      val baseUrl = server.url("/")

      EmberClientBuilder.default[IO].build.use(client => {
        val backoff = RetryPolicy.exponentialBackoff(1.second, 2)
        val retryPolicy = RetryPolicy[IO](backoff = backoff)
        val retryingClient = Retry(retryPolicy)(client)
        val uri = Uri.unsafeFromString(baseUrl.uri().toString)
        val request = Request[IO](GET, uri)
        retryingClient.run(request).use {
          response => IO.pure(response.status.code)
        }
      }).asserting(_ shouldBe Ok.code)
    }

    "429 by default" in {
      server.enqueue(new MockResponse().setResponseCode(TooManyRequests.code))
      server.enqueue(new MockResponse().setResponseCode(Ok.code))
      server.start()
      val baseUrl = server.url("/")

      EmberClientBuilder.default[IO].build.use(client => {
        val backoff = RetryPolicy.exponentialBackoff(1.second, 2)
        val retryPolicy = RetryPolicy[IO](backoff = backoff)
        val retryingClient = Retry(retryPolicy)(client)
        val uri = Uri.unsafeFromString(baseUrl.uri().toString)
        val request = Request[IO](GET, uri)
        retryingClient.run(request).use {
          response => IO.pure(response.status.code)
        }
      }).asserting(_ shouldBe TooManyRequests.code)
    }

    "429 custom" in {
      server.enqueue(new MockResponse().setResponseCode(TooManyRequests.code))
      server.enqueue(new MockResponse().setResponseCode(Ok.code))
      server.start()
      val baseUrl = server.url("/")

      EmberClientBuilder.default[IO].build.use(client => {
        val backoff = RetryPolicy.exponentialBackoff(1.second, 2)
        val retriable: (Request[IO], Either[Throwable, Response[IO]]) => Boolean = (_, result) => result match {
          case Right(response) => response.status == TooManyRequests
          case _ => false
        }
        val retryPolicy = RetryPolicy[IO](retriable = retriable, backoff = backoff)
        val retryingClient = Retry(retryPolicy)(client)
        val uri = Uri.unsafeFromString(baseUrl.uri().toString)
        val request = Request[IO](GET, uri)
        retryingClient.run(request).use {
          response => IO.pure(response.status.code)
        }
      }).asserting(_ shouldBe Ok.code)
    }

    "default and 429 custom" in {
      server.enqueue(new MockResponse().setResponseCode(TooManyRequests.code))
      server.enqueue(new MockResponse().setResponseCode(InternalServerError.code))
      server.enqueue(new MockResponse().setResponseCode(Ok.code))
      server.start()
      val baseUrl = server.url("/")

      EmberClientBuilder.default[IO].build.use(client => {
        val backoff = RetryPolicy.exponentialBackoff(1.second, 3)
        val retriable: (Request[IO], Either[Throwable, Response[IO]]) => Boolean = {
          case (_, Right(response)) if response.status == TooManyRequests => true
          case other => RetryPolicy.defaultRetriable(other._1, other._2)
        }
        val retryPolicy = RetryPolicy[IO](retriable = retriable, backoff = backoff)
        val retryingClient = Retry(retryPolicy)(client)
        val uri = Uri.unsafeFromString(baseUrl.uri().toString)
        val request = Request[IO](GET, uri)
        retryingClient.run(request).use {
          response => IO.pure(response.status.code)
        }
      }).asserting(_ shouldBe Ok.code)
    }
  }
}
