package example

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.io.Source

class HelloTest extends AnyFlatSpec with Matchers {
  "The Hello object" should "say hello" in {
    val source = Source.fromResource("example/test.txt")
    val expected = source.getLines().mkString
    source.close()
    Hello.greeting shouldEqual expected
  }
}