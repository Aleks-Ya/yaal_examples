package scala.file

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.nio.file.Files

class ExistsTest extends AnyFlatSpec with Matchers {
  private val prefix = classOf[ExistsTest].getSimpleName
  private val suffix = ".tmp"

  it should "is directory exists" in {
    val dir = Files.createTempDirectory(prefix)
    Files.exists(dir) shouldBe true
    Files.notExists(dir) shouldBe false
  }

  it should "create a temporary file" in {
    val file = Files.createTempFile(prefix, suffix)
    Files.exists(file) shouldBe true
    Files.notExists(file) shouldBe false
  }

}
