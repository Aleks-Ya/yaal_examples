package scalatest.matcher.file

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.File
import java.nio.file.Files

class FileTest extends AnyFlatSpec with Matchers {
  private val file = File.createTempFile(getClass.getSimpleName, ".tmp")
  private val dir = Files.createTempDirectory(getClass.getSimpleName).toFile

  "File" should "exist" in {
    file should exist
    file.exists shouldBe true
  }

  "File" should "be a file" in {
    file should be a 'file
    file should not be 'directory
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
//    Files.writeString(file.toPath, content)
//    Files.readString(file.toPath) shouldEqual content
  }

}