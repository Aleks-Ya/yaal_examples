package catseffect.control.cancel

import cats.effect.IO
import cats.effect.kernel.Outcome
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration.DurationInt

class PollTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "App should use " - {
    "a poll" in {
      val testIO = IO.uncancelable { poll =>
        for {
          beforePoll <- IO.pure("started")
          afterPoll <- poll(IO.sleep(1.second) *> IO.pure("completed")).onCancel(IO.pure("cancelled"))
        } yield (beforePoll, afterPoll)
      }

      // Start the IO and cancel it after 500 milliseconds.
      val runWithCancel = for {
        fiber <- testIO.start
        _ <- IO.sleep(500.millis) *> fiber.cancel
        outcome <- fiber.join
      } yield outcome

      runWithCancel.asserting {
        case Outcome.Canceled() => succeed // expected outcome due to poll allowing cancellation
        case Outcome.Succeeded(resultIO) => fail(s"Expected cancelled but succeeded with $resultIO")
        case Outcome.Errored(e) => fail(s"Unexpected error: $e")
      }

    }
  }
}
