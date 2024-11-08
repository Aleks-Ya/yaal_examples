package scalatest.matcher.file

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import util.FileUtil

import java.io.File
import java.nio.file.Files

class FileTest extends AnyFlatSpec with Matchers {
  private val file = File.createTempFile(getClass.getSimpleName, ".tmp")
  private val dir = Files.createTempDirectory(getClass.getSimpleName).toFile

  "File" should "exist" in {
    file should exist
    file.exists shouldBe true
  }

  "File" should "not exist" in {
    val file = new File("absent-file.txt")
    file shouldNot exist
    file.exists shouldBe false
  }

  "File" should "be a file" in {
    file should be a 'file
    file should not be 'directory
    file should have length 0
    file.isFile shouldBe true
    file.isDirectory shouldBe false
  }

  "File" should "be a directory" in {
    dir should not be 'file
    dir should be a 'directory
    dir.isFile shouldBe false
    dir.isDirectory shouldBe true
  }

  it should "be a readable file" in {
    file shouldBe readable
  }

  it should "be a writable file" in {
    file shouldBe writable
  }

  "File" should "have content" in {
    val content = "abc"
    FileUtil.write(file, content)
    FileUtil.read(file) shouldEqual content
  }

  "File" should "have size" in {
    file should have length 0
    file.length() shouldEqual 0
    file.length() shouldBe 0

    FileUtil.write(file, "abc")
    file should have length 3
    //file should have length >(1L) //NOT WORK
    file.length() shouldEqual 3
    file.length() should be > 1L
  }

}