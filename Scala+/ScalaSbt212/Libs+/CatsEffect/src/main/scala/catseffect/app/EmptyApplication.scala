package catseffect.app

import cats.effect.{ExitCode, IO, IOApp}

object EmptyApplication extends IOApp {
  def run(args: List[String]): IO[ExitCode] = IO(ExitCode.Success)
}
