package scala.file

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.File
import java.nio.file.Files

class ListFilesInDir extends AnyFlatSpec with Matchers {

  it should "list all files in a directory" in {
    val dir = Files.createTempDirectory("files").toFile
    val file1 = new File(dir, "a1.txt")
    val file2 = new File(dir, "a2.txt")
    val file3 = new File(dir, "b3.bin")

    file1.createNewFile() shouldBe true
    file2.createNewFile() shouldBe true
    file3.createNewFile() shouldBe true

    val files = dir.listFiles

    files should contain only(file1, file2, file3)
  }

  it should "filter files in a directory" in {
    val dir = Files.createTempDirectory("files").toFile
    val file1 = new File(dir, "a1.txt")
    val file2 = new File(dir, "a2.txt")
    val file3 = new File(dir, "b3.bin")

    file1.createNewFile() shouldBe true
    file2.createNewFile() shouldBe true
    file3.createNewFile() shouldBe true

    val filesStartsA = dir.listFiles.filter(_.getName.startsWith("a"))

    filesStartsA should contain only(file1, file2)
  }

  it should "find specific file in a directory" in {
    val dir = Files.createTempDirectory("files").toFile
    val file1 = new File(dir, "a1.txt")
    val file2 = new File(dir, "a2.txt")
    val file3 = new File(dir, "b3.bin")
    val file4 = new File(dir, "b4.bin")

    file1.createNewFile() shouldBe true
    file2.createNewFile() shouldBe true
    file3.createNewFile() shouldBe true
    file4.createNewFile() shouldBe true

    val file = dir.listFiles.filter(_.getName.startsWith("b"))(0)

    file shouldBe file3
  }

}
