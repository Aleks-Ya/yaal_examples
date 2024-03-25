package catseffect.control

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import java.util.concurrent.TimeoutException
import scala.concurrent.duration.DurationInt

class CancellableTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "Fiber" - {
    "cancel by timeout" in {
      assertThrows[TimeoutException] {
        lazy val loop: IO[Unit] = IO.println("loop until cancel..") >> IO.sleep(500.millis) >> loop
        val timeout = loop.timeout(2.seconds)
        timeout.unsafeRunSync()
      }
    }
  }
}
