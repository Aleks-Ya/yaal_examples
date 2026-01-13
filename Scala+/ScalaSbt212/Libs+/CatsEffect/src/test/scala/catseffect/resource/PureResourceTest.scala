package catseffect.resource

import cats.effect.testing.scalatest.AsyncIOSpec
import cats.effect.{IO, Resource}
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers
import util.FileUtil

import java.nio.file.Path

/**
 * Use the "Bracket" pattern to manage a resource (open, use, close).
 */
class PureResourceTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "App should pure" - {
    "a resource" in {
      val file1 = Resource.pure[IO, Path](FileUtil.writeNewTmpFile("content1"))
      val file2 = Resource.pure[IO, Path](FileUtil.writeNewTmpFile("content2"))
      val file3 = Resource.pure[IO, Path](FileUtil.createTmpFile())

      val concat: IO[Path] = (
        for {
          in1 <- file1
          in2 <- file2
          out <- file3
        } yield (in1, in2, out)
        ).use { case (file1, file2, file3) =>
        for {
          bytes1 <- read(file1)
          bytes2 <- read(file2)
          _ <- write(file3, bytes1 ++ bytes2)
        } yield file3

      }
      val file = concat.unsafeRunSync()
      FileUtil.read(file) shouldEqual "content1content2"
    }
  }

  def read(file: Path): IO[String] = IO.pure(FileUtil.read(file))

  def write(file: Path, bytes: String): IO[Unit] = IO.pure(FileUtil.write(file, bytes))

}
