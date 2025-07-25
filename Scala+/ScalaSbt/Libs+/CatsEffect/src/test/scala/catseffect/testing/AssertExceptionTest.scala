package catseffect.testing

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import java.io.IOException

class AssertExceptionTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "It should assert exceptions" - {

    "use assertThrowsWithMessage" in {
      IO(throw new IOException("failure"))
        .assertThrowsWithMessage[IOException]("failure")
    }

    "use assertThrows" in {
      assertThrows[IOException] {
        IO(throw new IOException("failure")).unsafeRunSync()
      }
    }

  }
}