package scala.number

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class IncrementNumberTest extends AnyFlatSpec with Matchers {

  it should "increment" in {
    var a = 1
    a += 2
    a shouldEqual 3
  }

  it should "decrement" in {
    var a = 3
    a -= 2
    a shouldEqual 1
  }

}
