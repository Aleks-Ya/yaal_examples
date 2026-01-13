package scala.file

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.nio.file.Path

class UriTest extends AnyFlatSpec with Matchers {

  it should "Path to URI" in {
    val userHome = System.getProperty("user.home")
    val path = Path.of(userHome, "abc.txt")
    val uri = path.toUri
    uri.toString should startWith("file:///")
  }

}
