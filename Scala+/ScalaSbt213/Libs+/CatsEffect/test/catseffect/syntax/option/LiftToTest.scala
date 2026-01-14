package catseffect.syntax.option

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import cats.implicits.catsSyntaxOption
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class LiftToTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "Effect created from liftTo" - {

    "Some option" in {
      val option = Some(1)
      val io = option.liftTo[IO](new RuntimeException("No value"))
      io.asserting(_ shouldBe 1)
    }

    "None option" in {
      val option = None
      val io = option.liftTo[IO](new RuntimeException("No value"))
      io.assertThrowsWithMessage[RuntimeException]("No value")
    }

  }
}
