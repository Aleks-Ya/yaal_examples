package catseffect.control.cancel

import cats.effect.testing.scalatest.AsyncIOSpec
import cats.effect.{ExitCode, IO}
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration.DurationInt

class UnCancellableTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "Fiber" - {
    "uncancelable" in {
      val io = for {
        _ <- IO.println("Starting...")
        fiber <- IO.uncancelable { _ =>
          IO.println("Hello, world!") >>
            IO.sleep(2.seconds) *> IO.println("Executed despite it was cancelled!") // Even if outer fiber is cancelled
        }.start
        _ <- IO.sleep(1.second)
        _ <- IO.println("Attempting to cancel (but it won't work for the uncancelable part)...")
        _ <- fiber.cancel // This will cancel the fiber, but the uncancelable block will continue
        _ <- IO.println("Fiber canceled")
        _ <- IO.sleep(3.seconds) // Wait long enough to see the uncancelable part finish
        _ <- IO.println("Done.")
      } yield ExitCode.Success
      io.asserting(_ shouldBe ExitCode.Success)
    }
  }
}
