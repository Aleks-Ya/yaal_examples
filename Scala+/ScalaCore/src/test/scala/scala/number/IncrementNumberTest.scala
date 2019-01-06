package scala.number

import org.scalatest.{FlatSpec, Matchers}

class IncrementNumberTest extends FlatSpec with Matchers {

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
