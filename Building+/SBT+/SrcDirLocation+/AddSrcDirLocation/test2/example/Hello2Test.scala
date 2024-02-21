package example

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.io.Source

class Hello2Test extends AnyFlatSpec with Matchers {
  "The Hello object 2" should "say hello 2" in {
    val source1 = Source.fromResource("example/test1.txt")
    val expected1 = source1.getLines().mkString
    source1.close()

    val source2 = Source.fromResource("example/test2.txt")
    val expected2 = source2.getLines().mkString
    source2.close()

    Hello.greeting shouldEqual s"$expected1 $expected2"
  }
}
