package catseffect.ffi

import cats.effect.testing.scalatest.AsyncIOSpec
import cats.effect.{Async, IO}
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import java.util.concurrent.atomic.AtomicLong
import scala.language.higherKinds

/**
 * @see https://typelevel.org/cats-effect/docs/typeclasses/async
 */
class AsyncTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  val counter = new AtomicLong()

  "App should execute in async mode" - {

    "code without return value" in {
      val ioExample: IO[Int] = asyncExample[IO]
      ioExample.asserting(_ shouldBe 42)
    }

  }

  def asyncExample[F[_] : Async]: F[Int] = {
    Async[F].async_[Int] { cb =>
      val result = 42
      cb(Right(result))
    }
  }

}
