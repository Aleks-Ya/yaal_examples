package catseffect.error

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class ExceptionTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "Fiber should" - {
    "throw an exception" in {
      assertThrows[RuntimeException] {
        IO(throw new RuntimeException("failure")).unsafeRunSync()
      }
    }
  }
}
