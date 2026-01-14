package scala.collection.list

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FlatTest extends AnyFlatSpec with Matchers {

  it should "covert list of list to list" in {
    val list = List(List(1, 2), List(3, 4))
    val list2 = list.flatten
    list2 should contain inOrderOnly(1, 2, 3, 4)
  }

}
