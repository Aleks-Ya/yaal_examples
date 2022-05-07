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

}
