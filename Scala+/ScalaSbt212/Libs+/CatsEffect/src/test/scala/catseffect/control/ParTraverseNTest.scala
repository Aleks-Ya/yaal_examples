package catseffect.control

import cats.effect.IO
import cats.effect.implicits.concurrentParTraverseOps
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration.DurationInt

class ParTraverseNTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "App should" - {
    "limit concurrent fibers" in {
      val task1: IO[String] = IO {
        println("Running task1")
        "Hello"

      }
      val task2: IO[String] = IO {
        println("Running task2")
        "World"
      }
      val ios = List(task1, task2)
      val limited = ios.parTraverseN(2)(processItem)
      limited.asserting(_ shouldBe List("HELLO", "WORLD"))
    }
  }

  def processItem(item: IO[String]): IO[String] = item.map(_.toUpperCase)
    .flatMap(value => IO.sleep(100.millis).as(value))
}
