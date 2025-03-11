package http4s

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.http4s.ember.client.EmberClientBuilder
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import java.util.concurrent.TimeUnit.SECONDS
import scala.concurrent.TimeoutException
import scala.concurrent.duration.Duration

class TimeoutTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  private val timeoutSec = 2
  private val client = EmberClientBuilder.default[IO].withTimeout(Duration.create(timeoutSec, SECONDS)).build

  "HttpClient" - {
    "should fail with timeout" in {
      val io = client.use(c => {
        val delaySec = timeoutSec * 2
        val url = s"https://httpbin.io/delay/$delaySec"
        c.get(url)(response => IO(response.status.code))
      })
      io.assertThrows[TimeoutException]
    }
  }
}
