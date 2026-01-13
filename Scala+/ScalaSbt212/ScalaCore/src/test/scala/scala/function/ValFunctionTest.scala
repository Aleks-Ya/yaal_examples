package scala.function

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ValFunctionTest extends AnyFlatSpec with Matchers {

  "without return type" should "works" in {
    val add = (a: Int, b: Int) => a + b
    add(1, 2) shouldEqual 3
  }

  "with return type" should "works" in {
    val add: (Int, Int) => Int = (x, y) => x + y
    add(1, 2) shouldEqual 3
  }

  "with return type with braces" should "works" in {
    val add: (Int, Int) => Int = (x, y) => {
      x + y
    }
    add(1, 2) shouldEqual 3
  }

  it should "declare a function without parameters" in {
    val giveThree = () => 3
    giveThree() shouldEqual 3
  }

}
