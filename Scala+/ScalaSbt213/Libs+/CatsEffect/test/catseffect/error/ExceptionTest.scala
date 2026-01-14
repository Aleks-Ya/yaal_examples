package catseffect.error

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import java.io.IOException

class ExceptionTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "Fiber should" - {
    "throw an exception" in {
      assertThrows[IOException] {
        IO(throw new IOException("failure")).unsafeRunSync()
      }
    }

    "raise error" in {
      assertThrows[IOException] {
        IO.raiseError(new IOException("failure")).unsafeRunSync()
      }
    }

    "handle error" in {
      IO.raiseError(new Exception("boom")).handleErrorWith { error =>
        IO.println(s"Error: ${error.getMessage}")
      }
    }

    "handle error and throw another error" in {
      assertThrows[IOException] {
        IO.raiseError(new Exception("boom")).handleErrorWith { error =>
          IO.raiseError(new IOException(error.getMessage))
        }.unsafeRunSync()
      }
    }

    "attempt" in {
      val boom: IO[Unit] = IO.raiseError(new Exception("boom"))
      val safeIo = boom.attempt
      safeIo.unsafeRunSync() match {
        case Left(ex) => println(s"An error occurred: ${ex.getMessage}")
        case Right(value) => println(s"Success: $value")
      }
      succeed
    }
  }
}
