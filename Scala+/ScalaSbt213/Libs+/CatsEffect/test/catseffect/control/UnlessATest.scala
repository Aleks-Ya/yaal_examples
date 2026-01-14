package catseffect.control

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import cats.implicits.catsSyntaxApplicativeByName
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class UnlessATest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "App should use IO#unlessA" - {

    "for condition" in {
      var a = 0
      IO.println("Running test").unlessA(cond = {
        a = a + 1
        a > 5
      })
    }

  }
}