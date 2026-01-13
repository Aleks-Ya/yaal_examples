package scala.file

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.nio.file.Files

class TempFileTest extends AnyFlatSpec with Matchers {
  private val prefix = classOf[TempFileTest].getSimpleName
  private val suffix = ".tmp"

  it should "create a temporary directory" in {
    val tmpDir = Files.createTempDirectory(prefix).toFile
    tmpDir should exist
  }

  it should "create a temporary file" in {
    val tmpFile = Files.createTempFile(prefix, suffix).toFile
    tmpFile should exist
  }

}
