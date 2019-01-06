package scala.collection.array

import org.scalatest.{FlatSpec, Matchers}

class ArrayCreateTest extends FlatSpec with Matchers {

  it should "create an array with specified elements" in {
    val arr = Array(1, 2, 3)
    arr should contain allOf(1, 2, 3)
  }

  it should "create an array with specified length" in {
    val arr = Array.ofDim[String](2)
    arr should have size 2
  }

}
