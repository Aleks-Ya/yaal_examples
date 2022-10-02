package scalatest.matcher.file

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.nio.file.Files

class FileTest extends AnyFlatSpec with Matchers {
  private val prefix: String = classOf[FileTest].getSimpleName
  private val suffix = ".tmp"

  it should "check file exists" in {
    val tmpFile = Files.createTempFile(prefix, suffix).toFile
    tmpFile should exist
    tmpFile.exists shouldBe true
  }

  it should "is a file" in {
    val file = Files.createTempFile(prefix, suffix).toFile
    file should be a 'file
    file should not be 'directory
    file.isFile shouldBe true
    file.isDirectory shouldBe false
  }

  it should "is a dir" in {
    val dir = Files.createTempDirectory(prefix).toFile
    dir should not be 'file
    dir should be a 'directory
    dir.isFile shouldBe false
    dir.isDirectory shouldBe true
  }

}