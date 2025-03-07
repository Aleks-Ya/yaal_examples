package catseffect.syntax.option

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class PureTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "App creates a constant IO" - {

    "from a String" in {
      val io = IO.pure("Hello").map(_ + " World")
      io.asserting(_ shouldBe "Hello World")
    }

    "from a Int" in {
      val io1 = IO.pure(3)
      val io2 = IO.pure(4)
      val io = for {
        n1 <- io1
        n2 <- io2
      } yield n1 * n2
      io.asserting(_ shouldBe 12)
    }

  }
}
