package catseffect.control

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import cats.implicits.catsSyntaxTuple2Parallel
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class ParTupledTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "Fiber" - {
    "execute 2 methods in parallel" in {
      val task1: IO[Int] = IO {
        println("Running task1")
        1
      }
      val task2: IO[String] = IO {
        println("Running task2")
        "Hello"
      }
      val io = (task1, task2).parTupled
      io.asserting(_ shouldBe ((1, "Hello")))
    }
  }
}
