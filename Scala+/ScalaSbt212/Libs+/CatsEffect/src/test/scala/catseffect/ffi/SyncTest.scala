package catseffect.ffi

import cats.effect.IO
import cats.effect.kernel.Sync
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers
import util.FileUtil

import java.nio.file.Path
import java.util.concurrent.atomic.AtomicLong
import scala.language.higherKinds

/**
 * @see https://typelevel.org/cats-effect/docs/typeclasses/sync
 */
class SyncTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  val counter = new AtomicLong()

  "App should execute in sync mode" - {

    "non-blocking code without return value" in {
      val ioProgram: IO[Unit] = program[IO]
      ioProgram.assertNoException
    }

    "non-blocking code with return value" in {
      val ioProgram: IO[Long] = program2[IO]
      ioProgram.asserting(_ shouldBe 1L)
    }

    "blocking code with return value" in {
      val ioProgram: IO[Path] = program3[IO]
      ioProgram.asserting(_.toFile should exist)
    }
  }

  def program[F[_] : Sync]: F[Unit] = {
    Sync[F].delay {
      println("This is a side effect!")
    }
  }

  def program2[F[_] : Sync]: F[Long] = {
    Sync[F].delay {
      counter.incrementAndGet()
    }
  }

  def program3[F[_] : Sync]: F[Path] = {
    Sync[F].blocking {
      FileUtil.writeNewTmpFile("Hello, world!")
    }
  }
}
