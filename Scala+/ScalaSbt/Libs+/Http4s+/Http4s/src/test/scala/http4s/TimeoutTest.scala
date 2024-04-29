package http4s

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.http4s.ember.client.EmberClientBuilder
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import java.util.concurrent.TimeUnit.SECONDS
import scala.concurrent.duration.Duration
import scala.concurrent.{Future, TimeoutException}

class TimeoutTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  private val timeoutSec = 2
  private val client = EmberClientBuilder.default[IO].withTimeout(Duration.create(timeoutSec, SECONDS)).build

  "HttpClient" - {
    "should fail with timeout" in {
      recoverToExceptionIf[TimeoutException] {
        Future(client.use(c => {
          val delaySec = timeoutSec * 2
          val url = s"http://httpbin.io/delay/$delaySec"
          c.get(url)(response => IO(response.status.code))
        }).unsafeRunSync())
      } map { ex => ex shouldBe a[TimeoutException] }
    }
  }
}
