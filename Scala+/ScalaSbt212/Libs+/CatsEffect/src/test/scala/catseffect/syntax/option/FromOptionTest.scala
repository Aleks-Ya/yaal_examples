package catseffect.syntax.option

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class FromOptionTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "Effect created from Option" - {

    "Some option" in {
      val option = Some(1)
      val io = IO.fromOption(option)(new RuntimeException("No value"))
      io.asserting(_ shouldBe 1)
    }

    "None option" in {
      val option = None
      val io = IO.fromOption(option)(new RuntimeException("No value"))
      io.assertThrowsWithMessage[RuntimeException]("No value")
    }

  }
}
