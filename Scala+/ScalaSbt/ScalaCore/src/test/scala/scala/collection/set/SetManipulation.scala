package scala.collection.set

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable.ListBuffer

class SetManipulation extends AnyFlatSpec with Matchers {

  it should "iterate a set" in {
    val set = Set(1, 2, 3)
    val actual = ListBuffer[Int]()
    set.foreach(actual += _)
    actual should contain allOf(1, 2, 3)
  }

  it should "add element to an immutable set" in {
    val set = Set(1, 2)
    val set2 = set + 3
    set2 should contain allOf(1, 2, 3)
  }

  it should "add element to a mutable set" in {
    val set = scala.collection.mutable.Set(1, 2)
    set += 3
    set should contain allOf(1, 2, 3)
  }

}
