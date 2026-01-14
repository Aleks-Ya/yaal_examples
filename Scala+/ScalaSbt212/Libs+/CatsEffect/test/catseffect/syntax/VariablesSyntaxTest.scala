package catseffect.syntax

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

/**
 * "Step" == "Effect"
 */
class VariablesSyntaxTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "Fiber steps are declared in " - {

    "for-syntax" in {
      for {
        _ <- IO.println("Hello")
        e <- IO.pure(2)
        a = 3
        b = 4
        c = a + b + e
      } yield c
    }.asserting(_ shouldBe 9)

    "type annotations" in {
      for {
        _ <- IO.println("Hello")
        e <- IO.pure(2)
        a: Int = 3
        b: Int = 4
        c: Int = a + b + e
      } yield c
    }.asserting(_ shouldBe 9)

  }
}
