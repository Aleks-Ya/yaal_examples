package catseffect.app

import cats.effect.{ExitCode, IO, IOApp}

object ExceptionApplication extends IOApp {
  def run(args: List[String]): IO[ExitCode] = IO.raiseError(new Exception("Boom!"))
}
