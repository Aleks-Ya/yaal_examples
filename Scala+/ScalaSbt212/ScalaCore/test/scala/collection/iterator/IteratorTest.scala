package scala.collection.iterator

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class IteratorTest extends AnyFlatSpec with Matchers {

  it should "create an iterator" in {
    val iter = Iterator(1, 2, 3)
    iter should have size 3
  }

  it should "create an iterator from Array" in {
    val arr = Array(1, 2, 3)
    val iter = arr.iterator
    iter should have size 3
  }

  it should "iterator to List" in {
    val iter = Iterator(1, 2, 3)
    val list = iter.toList
    list shouldBe List(1, 2, 3)
  }

}
