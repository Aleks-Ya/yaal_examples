package catseffect.syntax

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import cats.implicits.catsSyntaxTuple2Semigroupal
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class CompareTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "Compare values within Effects" - {

    "by mapN" in {
      val io1 = IO(5)
      val io2 = IO(5)
      val same = (io1, io2).mapN(_ == _)
      same.asserting(_ shouldBe true)
    }

    "by for-comprehension" in {
      val io1 = IO(5)
      val io2 = IO(5)
      val same = for {
        value1 <- io1
        value2 <- io2
      } yield value1 === value2
      same.asserting(_ shouldBe true)
    }

  }
}
