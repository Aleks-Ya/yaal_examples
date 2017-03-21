package collection

import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable.ListBuffer

class FlatTest extends FlatSpec with Matchers {

  it should "covert list of list to list" in {
    val list = List(List(1, 2), List(3, 4))
    val list2 = list.flatten
    list2 should contain inOrderOnly(1, 2, 3, 4)
  }

}
