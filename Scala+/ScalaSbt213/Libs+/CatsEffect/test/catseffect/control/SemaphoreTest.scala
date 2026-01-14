package catseffect.control

import cats.effect.IO
import cats.effect.std.Semaphore
import cats.effect.testing.scalatest.AsyncIOSpec
import cats.syntax.parallel._
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class SemaphoreTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "App should use Semaphore" - {
    "to limit concurrent fibers" in {
      val test = for {
        semaphore <- Semaphore[IO](2)
        task1 = semaphore.permit.use { _ =>
          IO {
            println("Running task1")
            "Hello"
          }
        }
        task2 = semaphore.permit.use { _ =>
          IO {
            println("Running task2")
            "World"
          }
        }
        results <- List(task1, task2).parSequence
      } yield results

      test.asserting { results =>
        results should contain theSameElementsAs List("Hello", "World")
      }
    }
  }
}