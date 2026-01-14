package scala.file

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.File
import java.nio.file.Files

class ListFilesInDirTest extends AnyFlatSpec with Matchers {
  private val dir = Files.createTempDirectory("files").toFile
  private val file1 = new File(dir, "a1.txt")
  private val file2 = new File(dir, "a2.txt")
  private val file3 = new File(dir, "b3.bin")
  private val file4 = new File(dir, "b4.bin")
  file1.createNewFile() shouldBe true
  file2.createNewFile() shouldBe true
  file3.createNewFile() shouldBe true
  file4.createNewFile() shouldBe true

  it should "list all files in a directory" in {
    val files = dir.listFiles
    files should contain only(file1, file2, file3, file4)
  }

  it should "list all file names in a directory" in {
    val files = dir.list
    files should contain only(file1.getName, file2.getName, file3.getName, file4.getName)
  }

  it should "filter files in a directory" in {
    val filesStartsA = dir.listFiles.filter(_.getName.startsWith("a"))
    filesStartsA should contain only(file1, file2)
  }

  it should "find specific file in a directory" in {
    val files = dir.listFiles.filter(_.getName.startsWith("b"))
    files should contain only(file3, file4)
  }

}
