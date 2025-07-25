package catseffect.control

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import java.io.IOException

class IfMTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "App should use IO#ifM" - {

    "with named parameters" in {
      val test = IO.pure(true).ifM(
        ifTrue = IO.pure("small"),
        ifFalse = IO.pure("big")
      )
      test.asserting { results =>
        results shouldEqual "small"
      }
    }

    "throw exception on condition" in {
      val test = IO.pure(false).ifM(
        ifTrue = IO.pure("small"),
        ifFalse = IO.raiseError(new IOException("boom"))
      )
      test.assertThrowsWithMessage[IOException]("boom")
    }

  }
}