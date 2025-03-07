package catseffect.resource

import cats.effect.testing.scalatest.AsyncIOSpec
import cats.effect.{IO, Resource}
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers
import util.FileUtil

import java.nio.file.{Files, Path}

/**
 * Use the "Bracket" pattern to manage a resource (open, use, close).
 */
class MakeResourceTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "App should make" - {

    "a resource (open, use, close)" in {
      val file1 = FileUtil.writeNewTmpFile("content1")
      val file2 = FileUtil.writeNewTmpFile("content2")
      val file3 = FileUtil.createTmpFile()

      def fileResource(name: Path): Resource[IO, Path] = Resource.make(openFile(name))(file => close(file))

      val concat: IO[Unit] =
        (
          for {
            in1 <- fileResource(file1)
            in2 <- fileResource(file2)
            out <- fileResource(file3)
          } yield (in1, in2, out)
          ).use { case (file1, file2, file3) =>
          for {
            bytes1 <- read(file1)
            bytes2 <- read(file2)
            _ <- write(file3, bytes1 ++ bytes2)
          } yield ()

        }
      concat.unsafeRunSync()
      file3.toFile shouldNot exist //Cleaned
    }
  }

  def openFile(name: Path): IO[Path] = IO.pure(name)

  def read(file: Path): IO[String] = IO.pure(FileUtil.read(file))

  def write(file: Path, bytes: String): IO[Unit] = IO.pure(FileUtil.write(file, bytes))

  def close(file: Path): IO[Unit] = IO(Files.delete(file))
}
