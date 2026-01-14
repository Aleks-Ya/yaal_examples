package catseffect.control

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import cats.implicits.catsSyntaxApplicativeByName
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class WhenATest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "App should use IO#whenA" - {

    "for condition" in {
      IO.println("Running test").whenA(cond = true)
    }

  }
}