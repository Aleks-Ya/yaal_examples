package catseffect.testing

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class AssertTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "It should assert" - {

    "IO expression" in {
      IO.pure(true).ifM(
        ifTrue = IO.pure("small"),
        ifFalse = IO.pure("big")
      ).asserting { results =>
        results shouldEqual "small"
      }
    }

    "IO variable" in {
      val results = IO.pure(true).ifM(
        ifTrue = IO.pure("small"),
        ifFalse = IO.pure("big")
      )

      results.asserting { results =>
        results shouldEqual "small"
      }
    }

    "yield condition" in {
      val test = for {
        results <- IO.pure(true).ifM(
          ifTrue = IO.pure("small"),
          ifFalse = IO.pure("big")
        )
      } yield results

      test.asserting { results =>
        results shouldEqual "small"
      }
    }
  }

}