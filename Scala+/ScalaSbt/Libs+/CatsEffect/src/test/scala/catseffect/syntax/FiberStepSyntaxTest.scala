package catseffect.syntax

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

/**
 * "Step" == "Effect"
 */
class FiberStepSyntaxTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "Fiber steps are declared in " - {

    "multi-line step" in {
      IO {
        println("Hello")
        println("World")
      }
    }

    "for-syntax" in {
      for {
        _ <- IO.println("Hello")
        _ <- IO.println("World")
      } yield ()
    }

    "in flatMap-syntax" in {
      IO.println("Hello") flatMap { _ =>
        IO.println("World 1")
        IO.println("World 2")
      }
    }

    "ignoring output of 1st step (opt 1)" in {
      IO.println("Hello") >> IO.println("World 3") >> IO.println("World 4")
    }

    "ignoring output of 1st step (opt 2)" in {
      IO.println("Hello") *> IO.println("World 5") *> IO.println("World 6")
    }

  }
}
