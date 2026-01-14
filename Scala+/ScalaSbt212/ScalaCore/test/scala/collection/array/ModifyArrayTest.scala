package scala.collection.array

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ModifyArrayTest extends AnyFlatSpec with Matchers {

  it should "add element to array end" in {
    val arr = Array(1, 2, 3)
    val updated = arr :+ 4
    updated shouldBe Array(1, 2, 3, 4)
  }

  it should "add element to array beginning" in {
    val arr = Array(1, 2, 3)
    val updated = 4 +: arr
    updated shouldBe Array(4, 1, 2, 3)
  }

  it should "replace element" in {
    val arr = Array(1, 2, 3)
    val updated = arr.updated(1, 7)
    updated shouldBe Array(1, 7, 3)
  }

}
