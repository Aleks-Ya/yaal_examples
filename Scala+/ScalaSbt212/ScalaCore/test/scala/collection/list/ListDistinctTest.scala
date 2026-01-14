package scala.collection.list

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ListDistinctTest extends AnyFlatSpec with Matchers {

  it should "find distinct elements" in {
    val list = List("a", "b", "a")
    val list2 = list.distinct
    list2 shouldEqual List("a", "b")
  }

}
