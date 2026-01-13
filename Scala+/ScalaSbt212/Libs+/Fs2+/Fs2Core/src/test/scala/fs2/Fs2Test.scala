package fs2

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class Fs2Test extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "fs2" - {
    "print to console" in {
      Stream.emit("Hello, World!").evalMap(IO.println).compile.drain
    }

    "Stream to String" in {
      IO(Stream.emit("Hello, World!").compile.string)
    }.asserting(_ shouldEqual "Hello, World!")
  }
}
