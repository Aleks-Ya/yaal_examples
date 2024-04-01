package scala.collection.array

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ReadArrayTest extends AnyFlatSpec with Matchers {

  it should "get element of an array" in {
    val arr = Array(1, 2, 3)
    val element = arr(1)
    element shouldBe 2
  }
}
