package catseffect.control

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers
import util.FileUtil

class FileTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "App should" - {

    "read a text file" in {
      val expContent = "Hello, World!"
      val file = FileUtil.writeNewTmpFile(expContent).toFile
      val content = IO.pure(FileUtil.read(file))
      content.asserting(_ shouldBe expContent)
    }

    "write a text file" in {
      val file = FileUtil.createTmpFile().toFile
      val expContent = "Hello, World!"
      val io = IO.pure(FileUtil.write(file, expContent))
      io.unsafeRunSync()
      FileUtil.read(file) shouldEqual expContent
    }

  }
}
