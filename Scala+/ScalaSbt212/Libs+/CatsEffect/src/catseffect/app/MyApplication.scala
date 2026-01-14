package catseffect.app

import cats.effect.{ExitCode, IO, IOApp}

object MyApplication extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- IO.print("Enter your name: ")
      name <- IO.readLine
      _ <- IO.println("Hello, " + name)
    } yield ExitCode.Success
}
