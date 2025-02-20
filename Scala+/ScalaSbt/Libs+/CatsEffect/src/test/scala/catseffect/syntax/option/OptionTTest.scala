package catseffect.syntax.option

import cats.data.OptionT
import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class OptionTTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "Effect uses OptionT" - {

    "Some option" in {
      val optionInt: Option[Int] = Some(42)
      val numIo= IO.pure(optionInt)
      val optionT = OptionT(numIo)
      val io = optionT.map(_ + 1).getOrElse(1)
      io.asserting(_ shouldBe 43)
    }

    "None option" in {
      val optionInt: Option[Int] = None
      val numIo= IO.pure(optionInt)
      val optionT = OptionT(numIo)
      val io = optionT.map(_ + 1).getOrElse(7)
      io.asserting(_ shouldBe 7)
    }

  }
}
