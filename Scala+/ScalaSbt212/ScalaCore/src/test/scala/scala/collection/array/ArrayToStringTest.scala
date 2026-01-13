package scala.collection.array

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ArrayToStringTest extends AnyFlatSpec with Matchers {

  it should "convert an array to string (default delimiter)" in {
    val arr = Array(1, 2, 3)
    val str = arr.mkString
    str shouldBe "123"
  }


  it should "convert an array to string (custom delimiter)" in {
    val arr = Array(1, 2, 3)
    val str = arr.mkString("; ")
    str shouldBe "1; 2; 3"
  }

}
