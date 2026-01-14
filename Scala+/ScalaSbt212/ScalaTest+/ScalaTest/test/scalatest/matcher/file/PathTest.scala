package scalatest.matcher.file

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.nio.file.{Files, Paths}

class PathTest extends AnyFlatSpec with Matchers {
  private val prefix: String = classOf[PathTest].getSimpleName
  private val suffix = ".tmp"

  it should "check file exists" in {
    val path = Files.createTempFile(prefix, suffix)
    path.toFile should exist
    Files.exists(path) shouldBe true
  }

  it should "check file not exists" in {
    val path = Paths.get("absent-file.txt")
    path.toFile shouldNot exist
    Files.exists(path) shouldBe false
  }

  it should "is a file" in {
    val path = Files.createTempFile(prefix, suffix)
    path.toFile should be a 'file
    path.toFile should not be 'directory
    Files.isDirectory(path) shouldBe false
  }

  it should "is a dir" in {
    val path = Files.createTempDirectory(prefix)
    path.toFile should not be 'file
    path.toFile should be a 'directory
    Files.isDirectory(path) shouldBe true
  }

}