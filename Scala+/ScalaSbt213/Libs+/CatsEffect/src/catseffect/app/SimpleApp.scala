package catseffect.app

import cats.effect.{IO, IOApp}

object SimpleApp extends IOApp.Simple {
  val run: IO[Unit] = IO.println("Hello") >> IO.println("World")
}
