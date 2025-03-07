package catseffect.app

import cats.effect.{ExitCode, IO, IOApp}

object CancelledApplication extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    IO.canceled.attempt.flatMap {
      case Left(e) =>
        IO(println(s"IO operation canceled: $e")).as(ExitCode.Error)
      case Right(_) =>
        IO(println("IO completed successfully.")).as(ExitCode.Success)
    }
  }
}